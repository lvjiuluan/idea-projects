# FedCVT Semi-supervised Vertical Federated Learning with Cross-view Training

## 摘要

联邦学习允许多方协作构建机器学习模型，而无需暴露数据。特别是，垂直联邦学习 (VFL) 使参与方能够基于对齐样本的分布式特征构建联合机器学习模型。然而，VFL 要求所有参与方共享足够数量的对齐样本。在现实中，对齐样本集可能很小，导致大部分非对齐数据未被使用。在本文中，我们提出了联邦跨视图训练 (FedCVT)，这是一种半监督学习方法，可以在对齐样本有限的情况下提高 VFL 模型的性能。更具体地说，FedCVT 估计缺失特征的表示，预测未标记样本的伪标签以扩展训练集，并基于扩展训练集的不同视图联合训练三个分类器，以提高 VFL 模型的性能。FedCVT 不需要各方共享其原始数据和模型参数，从而保护数据隐私。我们在 NUS-WIDE、Vehicle 和 CIFAR10 数据集上进行了实验。实验结果表明，FedCVT 显著优于仅利用对齐样本的普通 VFL。最后，我们进行了消融研究，以调查 FedCVT 的每个组件对 FedCVT 性能的贡献。

## 6 实验评估

我们在三个公共数据集上进行了实验，这些数据集分别是：(1) NUS-WIDE 数据集 [4]；(2) Vehicle 数据集 [7]；(3) CIFAR-10 [20]，以验证我们提出的 FedCVT 方法。

### 6.1 超参数  
FedCVT-VFL 结合了多种技术来提高 VFL（联邦学习）的性能，因此引入了各种超参数——具体来说，包括损失权重 \( \lambda \) 值、标签概率阈值 \( t \)、用于表示估计的锐化温度 \( T \) 以及学习率。在实验中：

- 对于 **NUS-WIDE**，我们设置 \( \lambda_{1,2,3,4,5} = 0.1 \)，\( t = 0.5 \)，\( T = 0.5 \)，学习率为 0.005。  
- 对于 **Vehicle**，我们设置 \( \lambda_{1,2,3,4,5} = 0.1 \)，\( t = 0.5 \)，\( T = 1.0 \)，学习率为 0.005。  
- 对于 **CIFAR-10**，我们设置 \( \lambda_{1,4,5} = 0.1 \)，\( \lambda_{2,3} = 0.01 \)，\( t = 0.6 \)，\( T = 0.1 \)，学习率为 0.001。  

在所有实验中，我们使用 Adam [19] 优化器。

### 6.2 基线模型  

我们考虑了三种基线模型：(1) 参与方 A 的本地模型 \( f^A \)，(2) 标准 VFL（vanilla-VFL），以及 (3) FTL [26]。  

参与方 A 的本地模型是在 **所有本地样本** 上训练的，而标准 VFL 是在 **对齐样本**（aligned samples）上训练的，即仅使用参与方 A 和参与方 B 之间对齐的样本，而不使用未对齐的样本。FTL 利用参与方 A 的所有样本，并结合对齐样本来帮助参与方 B 构建分类模型。  

我们将参与方 A 的本地模型作为基线模型，因为值得研究在对齐样本数量有限的情况下，VFL 是否比本地模型表现更优。采用标准 VFL 和 FTL 进行实验，以验证我们的 FedCVT-VFL 方法是否有效。  

需要注意的是，FTL 旨在仅为参与方 B 构建模型，因此它没有一个能够同时利用两方输入的联邦模型。为了确保公平比较，我们在本地模型的基础上添加了一个联邦模型，并将该修改后的 FTL 记为 **mFTL**。

### 6.3 实验结果  

#### 6.3.1 NUS-WIDE  

NUS-WIDE 数据集包含 634 维的低级图像特征，这些特征是从 Flickr 图像及其相关的 1,000 个文本标签中提取的，同时数据集中包含 81 个真实标签。在本研究中，我们考虑解决一个 10 类分类问题，其中数据由参与方 A 和参与方 B 进行联邦共享。  

在我们的设置中，每个参与方有 56,000 个训练样本、8,000 个对齐的验证样本和 10,000 个对齐的测试样本。每个参与方使用两个本地神经网络，每个神经网络包含一个隐藏层，并具有 96 个单元来学习来自本地输入样本的表示。然后，每个参与方将学习到的表示输入到其本地 softmax 分类器 \( f^p \)（其中 \( p \in \{A, B\} \)，其维度为 192，即 \( 96 \times 2 \)），以及联邦 softmax 层 \( f^{AB} \)（其维度为 384，即 \( 96 \times 2 \times 2 \)），进行联邦训练。  

在 NUS-WIDE 数据集上，我们设计了两种实验场景：
- 在第一种场景（scenario-1）中，我们将 634 维图像特征分配给参与方 A，而 1,000 维文本特征分配给参与方 B。  
- 在第二种场景（scenario-2）中，分配方式与 scenario-1 相反。  

在这两种场景下，参与方 A 都拥有数据标签。我们选择这两种场景是因为图像和文本属于不同的模态（modality），它们具有不同的判别能力，我们希望研究这些差异如何影响 FedCVT-VFL 的性能。  

表 1 展示了 FedCVT-VFL 与基线方法在不同对齐样本数量（从 250 到 8,000）的准确率比较。FedCVT-VFL 明显优于 mFTL [26]，并且在两个场景下均大幅优于标准 VFL（vanilla-VFL）（详见图 6）。例如，在 250 个对齐样本的情况下，FedCVT-VFL 在 scenario-1 和 scenario-2 中分别比 vanilla-VFL 提高了 13.4 和 17.0 个百分点的准确率。  

当对齐样本数增加到 8,000 时，FedCVT-VFL 在 scenario-1 和 scenario-2 中的准确率分别比 vanilla-VFL 提高了 1.8 和 3.8 个百分点。此外，即使在仅有少量对齐样本的情况下，FedCVT-VFL 也能超越本地模型。例如，在 scenario-1 中，FedCVT-VFL 在仅有 250 个对齐样本时就能超越本地模型，而在 scenario-2 中仅需 500 个对齐样本即可超越本地模型。  

如表 1 所示，scenario-2 中的本地模型明显优于 scenario-1（提高了 11.4 个百分点），这表明文本特征在 NUS-WIDE 数据集中比图像特征具有更强的判别能力。因此，在 scenario-2 中，当对齐样本数量较少时，利用补充的图像特征对 vanilla-VFL 的贡献较小。这在一定程度上解释了为什么在 scenario-2 中，当对齐样本数量不足（例如 \( \leq 2,000 \)）时，vanilla-VFL 的模型并未优于本地模型（详见图 6(b) 中的蓝色实线与红色虚线）。相比之下，在 scenario-1 中，参与方 A 通过利用补充的文本特征显著受益。

表1. 在NUS - WIDE数据集上，不同数量的对齐样本情况下，联邦视觉变换器垂直联邦学习（FedCVT - VFL）与参与方A的本地模型、多任务联邦迁移学习（mFTL）和普通垂直联邦学习（Vanilla - VFL）的测试准确率（%）对比

![image-20250219214553724](D:\Typora\images\image-20250219214553724-1740051529476-1.png)

请注意，本地模型是基于参与方A的56,000个本地样本进行训练的，因此它不受对齐样本数量的影响。Δ表示联邦视觉变换器垂直联邦学习（FedCVT - VFL）相较于普通垂直联邦学习（Vanilla - VFL）的性能提升。

![image-20250219215135973](D:\Typora\images\image-20250219215135973.png)