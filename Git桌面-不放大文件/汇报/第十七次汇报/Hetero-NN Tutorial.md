# Hetero-NN教程

在异构联合学习（垂直分割数据）环境下，多个方拥有针对相同用户样本的不同特征集。联邦学习使这些方能够在不共享实际数据的情况下协同训练模型。在FATE-2.0中，我们推出了全新的Hetero-NN框架，允许您快速设置一个异构联合神经网络学习任务。由于我们的框架基于PyTorch和transformers开发，您可以轻松地将现有的数据集和模型无缝集成到我们的框架中。

在本教程中，我们将向您展示如何在本地运行一个Hetero-NN任务，而不使用FATE-Pipeline。您可以参考这个例子进行本地模型实验、算法修改和测试。

此外，FATE-2.0还提供了两种保护策略：SSHE和FedPass。我们将在本教程中展示如何使用这些策略。

## 设置Hetero-NN步骤

要运行Hetero-NN任务，需要执行以下几个步骤：

1. 在新的Python脚本中导入所需的类
2. 为guest方和host方准备数据、数据集、模型、损失函数和优化器
3. 配置训练参数；初始化Hetero-NN模型；设置保护策略
4. 准备训练器
5. 运行训练脚本

## 导入所需类

在FATE-2.0中，我们的神经网络（NN）框架是基于PyTorch和transformers库构建的。这种集成使得将现有模型和数据集整合到联邦训练中变得更加简便。在我们的HeteroNN模块中，我们使用HeteroNNTrainerGuest和HeteroNNTrainerHost分别在guest方和host方训练模型。它们是基于huggingface的训练器开发的，因此您可以通过TrainingArguments类以相同的方式指定训练参数。

我们还提供了HeteroNNModelGuest和HeteroNNModelHost来封装顶部/底部模型和聚合层，并为训练器提供统一接口。您可以定义自己的底部/顶部模型结构，并将其传递给HeteroNNModelGuest和HeteroNNModelHost。我们提供两种保护策略：SSHE和FedPass。您可以通过SSHEArgument和FedPassArgument在HeteroNNModelGuest和HeteroNNModelHost中指定它们。

```python
import torch as t
from fate.arch import Context
from fate.ml.nn.hetero.hetero_nn import HeteroNNTrainerGuest, HeteroNNTrainerHost, TrainingArguments
from fate.ml.nn.model_zoo.hetero_nn_model import HeteroNNModelGuest, HeteroNNModelHost
from fate.ml.nn.model_zoo.hetero_nn_model import SSHEArgument, FedPassArgument, TopModelStrategyArguments
```

## 使用SSHE的表格数据示例

在本节中，我们展示如何使用我们的NN框架，它是一个二分类任务，特征为表格数据。

您可以从以下链接下载示例数据：

- [breast_hetero_guest.csv]()
- [breast_hetero_host.csv]()

并将它们与您的Python脚本放在同一目录下。

在这个例子中，我们将使用SSHE策略来保护数据，因此，SSHE聚合层将负责聚合guest方和host方的前向传播，并将梯度反向传播回guest方和host方。

### FATE上下文

FATE-2.0使用一个上下文对象来配置运行环境，包括方设置（guest、host及它们的party ID）。我们可以通过调用create_context函数来创建上下文对象。

```python
def create_ctx(party, session_id='test_fate'):
    parties = [("guest", "9999"), ("host", "10000")]
    if party == "guest":
        local_party = ("guest", "9999")
    else:
        local_party = ("host", "10000")
    context = create_context(local_party, parties=parties, federation_session_id=session_id)
    return context
```

如果我们使用launch()函数运行任务（稍后将解释），它可以自动处理上下文的创建，本章将介绍上下文的概念并向您展示如何手动创建它。

### 准备

在开始训练之前，就像在PyTorch中一样，我们首先定义模型结构、准备数据、选择损失函数并实例化优化器。以下代码演示了数据、数据集、模型、损失和优化器的准备。在异构神经网络（Hetero-NN）环境中，这与同构（homo）联合学习场景不同，特征和模型被分割，每个方管理自己的一部分数据。代码使用'ctx'来区分guest和host代码：guest方有标签和10个特征，因此它创建顶部/模型，而host方只有20个特征且没有标签，因此只创建底部模型。在初始化HeteroNNGuestModel和HeteroNNHostModel时，SSHEArgument被传递以在训练期间构建一个安全共享和同态加密（SSHE）聚合层，保护前向和反向过程。

类似于使用HuggingFace训练器，TrainingArguments用于设置训练参数。**请注意，Hetero-NN目前不支持多GPU训练，且SSHE层与GPU训练不兼容。**

一旦模型和数据集准备好，我们就可以开始训练过程了。

```python
def get_setting(ctx):
    from fate.ml.nn.dataset.table import TableDataset
    # 准备数据
    if ctx.is_on_guest:
        ds = TableDataset(to_tensor=True)
        ds.load("./breast_hetero_guest.csv")

        bottom_model = t.nn.Sequential(
            t.nn.Linear(10, 8),
            t.nn.ReLU(),
        )
        top_model = t.nn.Sequential(
            t.nn.Linear(8, 1),
            t.nn.Sigmoid()
        )

        model = HeteroNNModelGuest(
            top_model=top_model,
            bottom_model=bottom_model,
            agglayer_arg=SSHEArgument(
                guest_in_features=8,
                host_in_features=8,
                out_features=8,
                layer_lr=0.01
            )
        )
        optimizer = t.optim.Adam(model.parameters(), lr=0.01)
        loss = t.nn.BCELoss()

    else:
        ds = TableDataset(to_tensor=True)
        ds.load("./breast_hetero_host.csv")
        bottom_model = t.nn.Sequential(
            t.nn.Linear(20, 8),
            t.nn.ReLU(),
        )

        model = HeteroNNModelHost(
            bottom_model=bottom_model,
            agglayer_arg=SSHEArgument(
                guest_in_features=8,
                host_in_features=8,
                out_features=8,
                layer_lr=0.01
            )
        )
        optimizer = t.optim.Adam(model.parameters(), lr=0.01)
        loss = None

    args = TrainingArguments(
        num_train_epochs=3,
        per_device_train_batch_size=256
    )

    return ds, model, optimizer, loss, args
```

---

This is a partial translation of the tutorial. If you need further assistance with specific sections or more details on any part of the tutorial, feel free to ask!