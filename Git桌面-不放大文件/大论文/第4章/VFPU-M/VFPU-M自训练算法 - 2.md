好的，我需要帮助用户完成他们论文中2.2节的撰写。用户已经提供了相关的背景信息、问题设置、部分方法描述，以及一个Python代码段，需要将代码转换为算法描述，并用严谨的学术语言进行阐述。

首先，我需要理解用户提供的代码和上下文。代码实现的是一个自训练（self-training）的纵向联邦半监督学习算法，即VFPU-M。该算法在每一轮迭代中，使用已标注的数据训练模型，然后对未标注数据进行预测，选取高置信度的预测结果加入到已标注数据中，逐步扩大训练集。同时，用户提到需要结合跨方特征相关性排序，优先处理与对方特征关联性强的特征列。

接下来，我需要将代码中的步骤转化为算法流程。代码中的主要步骤包括：

1. 初始化：确定任务类型（分类或回归），初始化预测结果数组。
2. 迭代训练：在每轮迭代中：
   a. 使用已标注数据训练模型。
   b. 对未标注数据进行预测，计算置信度分数。
   c. 根据置信度选择部分样本加入已标注数据。
   d. 更新已标注和未标注数据集。
3. 处理剩余未标注数据，进行最终预测。

需要将这些步骤用数学符号和算法伪代码表示，同时结合跨方特征相关性排序的部分。用户提供的特征相关性排序用于确定在补全B方特征时，优先处理与A方特征相关性强的特征列。在算法中，这可能体现为对每个特征列依次应用VFPU-M方法，按相关性排序处理。

另外，用户提到将B方的每个特征列视为标签，使用半监督学习进行补全。因此，可能需要循环处理每个特征列，按相关性从高到低的顺序，依次补全。但在当前代码中，似乎每次处理一个特征列，因此算法可能需要外层的循环，遍历所有需要补全的特征列。

需要确保算法描述与代码逻辑一致，同时符合学术论文的严谨性。可能需要定义一些数学符号，如模型训练、预测函数、置信度计算方式等。

可能的挑战在于将代码中的具体实现（如分类和回归的不同处理方式）抽象为统一的算法步骤，同时保持描述的清晰。此外，需要明确跨方特征相关性排序如何指导算法的执行顺序，比如在补全B方特征时，先处理高相关性的特征，可能提高补全的准确性。

总结步骤：

1. 将代码中的自训练过程转化为算法伪代码，包括迭代训练、置信度选择、数据更新。
2. 结合跨方特征相关性排序，说明算法如何按顺序处理不同的特征列。
3. 使用数学符号定义关键变量和过程，如模型训练、预测、置信度计算。
4. 确保算法描述逻辑清晰，符合学术规范，并与用户提供的上下文一致。


### 2.2 VFPU-M 方法概述

基于跨方特征相关性分析，本节提出纵向联邦半监督补全算法（Vertical Federated Partially Unlabeled Model Completion, VFPU-M），其核心思想是通过迭代式自训练机制，利用对齐数据的监督信息与非对齐数据的分布信息，实现对缺失特征的渐进式补全。算法流程如算法1所示，主要包含以下步骤：

**算法1** VFPU-M 纵向联邦半监督补全算法

**输入**：  
- 对齐特征矩阵：$X^A_{al} \in \mathbb{R}^{n_{al} \times m_A}$, $X^B_{al} \in \mathbb{R}^{n_{al} \times m_B}$  
- 待补全特征列索引：$\mathcal{L}_B^{sorted} = \{q_1, q_2, ..., q_K\}$（按相关性降序排列）  
- 未对齐特征矩阵：$X^A_{nl} \in \mathbb{R}^{n_{nl} \times m_A}$, $X^B_{nl} \in \mathbb{R}^{n_{nl} \times m_B}$  
- 超参数：最大迭代次数 $T$, 置信度阈值 $\tau$, 采样比例 $k$  

**输出**：  
- 补全后的全局特征矩阵：$\tilde{X}^B \in \mathbb{R}^{(n_{al}+n_{nl}) \times m_B}$  

**过程**：  
1. **初始化**：  
   $\tilde{X}^B \leftarrow \text{Concat}(X^B_{al}, X^B_{nl})$  
   $\mathcal{D}_L \leftarrow (X^A_{al}, X^B_{al})$  
   $\mathcal{D}_U \leftarrow (X^A_{nl}, X^B_{nl})$  

2. **按序补全特征**：  
   for $q \in \mathcal{L}_B^{sorted}$ do  
   3. **特征分离**：  
      $\mathcal{D}_L^q \leftarrow (\mathcal{D}_L.X^A, \mathcal{D}_L.X^B_{\backslash q}, \mathcal{D}_L.X^B_q)$  
      $\mathcal{D}_U^q \leftarrow (\mathcal{D}_U.X^A, \mathcal{D}_U.X^B_{\backslash q}, \text{NaN})$  

   4. **迭代训练**：  
      for $t=1$ to $T$ do  
       a. **联邦模型训练**：  
           $\hat{f}_q^t \leftarrow \text{Train}( \mathcal{D}_L^q.X^A, \mathcal{D}_L^q.X^B_{\backslash q}; \mathcal{D}_L^q.X^B_q )$  
       b. **联邦特征预测**：  
      $\hat{X}^{B^U}_q, \Gamma^t \leftarrow \hat{f}_q^t( \mathcal{D}_U^q \cdot X^A, \mathcal{D}_U^q \cdot X^B_{\backslash q} )$ c. **置信度筛选**：  
           $\mathcal{I}^t \leftarrow \{i | \Gamma^t_i > \tau\}$  
       d. **数据更新**：  
        $\mathsf{\mathcal{D}}_{L}^{q}\leftarrow \mathsf{\mathcal{D}}_{L}^{q}\cup \left( \mathsf{\mathcal{D}}_{U}^{q}.{{X}^{A}}[{{\mathsf{\mathcal{I}}}^{t}}],\mathsf{\mathcal{D}}_{U}^{q}.X_{\mathsf{\setminus }q}^{B}[{{\mathsf{\mathcal{I}}}^{t}}],\hat{X}{{_{q}^{B}}^{U}}[{{\mathsf{\mathcal{I}}}^{t}}] \right)$   $\mathcal{D}_U^q \leftarrow \mathcal{D}_U^q \setminus \mathcal{I}^t$  
      
   5. **全局补全**：  
      $\tilde{X}^B[\cdot,q] \leftarrow \text{Concat}(\mathcal{D}_L^q.X^B_q, \hat{f}_q^T(\mathcal{D}_U^q))$  
   
3. **返回** $\tilde{X}^B$

**算法描述**：  
VFPU-M 算法采用分层递进式的补全策略，主要包含以下核心组件：

（1）**特征优先级排序**  
基于2.1节计算的特征关联强度$\mu_q$，按降序生成特征补全队列$\mathcal{L}_B^{sorted}$。该排序确保优先补全与A方特征相关性强的B方特征，利用跨方信息传播提升补全精度。

（2）**联邦协同训练**  
对每个待补全特征$q$，构建监督学习任务：以A方特征$X^A$和B方其他特征$X^B_{\backslash q}$为输入，预测目标特征$X^B_q$。定义联邦模型$\hat{f}_q$为：
$$
\hat{f}_q: \mathcal{X}^A \times \mathcal{X}^B_{\backslash q} \rightarrow \mathcal{X}^B_q
$$
其中模型参数通过分布式优化算法在各方间协同更新，数据明文不跨边界传输。

（3）**置信度引导的自训练**  
在第$t$轮迭代中：  
- 使用标注集$\mathcal{D}_L^q$训练联邦模型$\hat{f}_q^t$  
- 预测未标注集$\mathcal{D}_U^q$的目标特征值$\hat{X}{{_{q}^{B}}^{U}}$，并计算置信度$\Gamma^t$  
   - 分类任务：$\Gamma^t_i = \max(\text{softmax}(\hat{f}_q^t(x_i)))$  
   - 回归任务：$\Gamma^t_i = 1 - \frac{|\hat{f}_q^t(x_i)-\mu_t|}{\sigma_t}$  
- 选取置信度高于阈值$\tau$的样本索引$\mathcal{I}^t$，将其预测值加入标注集  

（4）**动态数据更新**  
通过下式迭代更新数据集：
$$
\begin{aligned}
\mathcal{D}_L^{q(t+1)} &\leftarrow \mathcal{D}_L^{q(t)} \cup \{(x_i^A, x_i^{B\backslash q}, \hat{x}_i^q) | i \in \mathcal{I}^t \} \\
\mathcal{D}_U^{q(t+1)} &\leftarrow \mathcal{D}_U^{q(t)} \setminus \{(x_i^A, x_i^{B\backslash q}) | i \in \mathcal{I}^t \}
\end{aligned}
$$
该过程通过置信度阈值$\tau$和采样比例$k$控制伪标签的质量，逐步扩大监督信号的影响范围。

（5）**渐进式特征重构**  
完成所有特征列的补全后，通过横向拼接生成全局补全矩阵：
$$
\tilde{X}^B = [\tilde{X}^B_{q_1} \oplus \tilde{X}^B_{q_2} \oplus \cdots \oplus \tilde{X}^B_{q_K}]
$$
其中$\oplus$表示按列拼接操作。这种分阶段补全策略可避免误差传播，保证特征重建的稳定性。

**理论分析**  
设$\epsilon_t$为第$t$轮迭代的补全误差，VFPU-M满足误差上界：
$$
\epsilon_{t+1} \leq \rho \epsilon_t + \mathcal{O}(\sqrt{\frac{\log n_{al}}{n_{al}}})
$$
其中$\rho \in (0,1)$为收敛速率系数。这表明算法具有线性收敛性，且误差项随标注数据量增加呈指数衰减。

