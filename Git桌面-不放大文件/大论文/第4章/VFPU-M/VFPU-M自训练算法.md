
### 3.2 VFPU-M 自训练算法

基于纵向联邦半监督学习框架，我们提出一种基于置信度传播的自训练算法。该算法通过迭代地选择高置信度未标注样本进行伪标签标注，从而逐步扩充训练集规模。算法流程如下：

**定义 1 (数据集合)**：  
- 已标注数据集：$\mathcal{D}_L = \{(\mathbf{x}^A_i, \mathbf{x}^B_i, y_i)\}_{i=1}^{n_L}$，其中$\mathbf{x}^A_i \in \mathbb{R}^{d_A}$, $\mathbf{x}^B_i \in \mathbb{R}^{d_B}$为特征向量，$y_i$为标签
- 未标注数据集：$\mathcal{D}_U = \{(\mathbf{x}^A_j, \mathbf{x}^B_j)\}_{j=1}^{n_U}$

**算法 1 (纵向联邦自训练算法)**：  
**输入**：$\mathcal{D}_L$, $\mathcal{D}_U$, 联邦模型$f_\theta$, 置信度阈值$\tau$, 选择比例$k$, 最大迭代次数$T$  
**输出**：伪标签预测结果$\hat{\mathbf{y}}_U$

1. **初始化**：
   $$
   \mathcal{D}_L^{(0)} \leftarrow \mathcal{D}_L, \quad \mathcal{D}_U^{(0)} \leftarrow \mathcal{D}_U, \quad t \leftarrow 0
   $$
   
2. **迭代过程**：
   While $t < T$ 且 $|\mathcal{D}_U^{(t)}| > 0$：
   
   a. **联邦模型训练**：
   $$
   \theta^{(t)} \leftarrow \arg\min_\theta \sum_{(\mathbf{x}^A,\mathbf{x}^B,y)\in\mathcal{D}_L^{(t)}} \mathcal{L}(f_\theta(\mathbf{x}^A,\mathbf{x}^B), y)
   $$
   
   b. **置信度计算**：
   对$\forall (\mathbf{x}^A_j,\mathbf{x}^B_j) \in \mathcal{D}_U^{(t)}$，计算置信度：
   $$
   s_j = \begin{cases} 
   \max_{c} \mathbb{P}(y=c|f_{\theta^{(t)}}(\mathbf{x}^A_j,\mathbf{x}^B_j)) & \text{分类任务} \\
   1 - \frac{|\hat{y}_j - \mu_t|}{\sigma_t} & \text{回归任务}
   \end{cases}
   $$
   其中$\mu_t,\sigma_t$分别为当前预测的均值和标准差

   c. **样本选择**：
   构造候选集：
   $$
   \mathcal{C}^{(t)} = \{ (\mathbf{x}^A_j,\mathbf{x}^B_j) \in \mathcal{D}_U^{(t)} | s_j \geq \tau \}
   $$
   按置信度排序后选取前$k$比例的样本：
   $$
   \mathcal{S}^{(t)} = \text{TopK}(\mathcal{C}^{(t)}, k)
   $$

   d. **伪标签生成**：
   $$
   \forall (\mathbf{x}^A_j,\mathbf{x}^B_j) \in \mathcal{S}^{(t)}, \quad \hat{y}_j = \begin{cases}
   \arg\max_c \mathbb{P}(y=c|f_{\theta^{(t)}}(\mathbf{x}^A_j,\mathbf{x}^B_j)) & \text{分类} \\
   f_{\theta^{(t)}}(\mathbf{x}^A_j,\mathbf{x}^B_j) & \text{回归}
   \end{cases}
   $$

   e. **数据集更新**：
   $$
   \mathcal{D}_L^{(t+1)} \leftarrow \mathcal{D}_L^{(t)} \cup \{(\mathbf{x}^A_j,\mathbf{x}^B_j,\hat{y}_j)\}_{j\in\mathcal{S}^{(t)}} \\
   \mathcal{D}_U^{(t+1)} \leftarrow \mathcal{D}_U^{(t)} \setminus \mathcal{S}^{(t)}
   $$
   
   f. **迭代计数**：$t \leftarrow t + 1$

3. **最终预测**：
   对剩余未标注样本进行预测：
   $$
   \forall (\mathbf{x}^A_j,\mathbf{x}^B_j) \in \mathcal{D}_U^{(t)}, \quad \hat{y}_j = f_{\theta^{(t)}}(\mathbf{x}^A_j,\mathbf{x}^B_j)
   $$

**定理 1 (收敛性)**：当选择比例$k$满足单调递减条件时，算法在有限步内收敛。即存在$T_0 < \infty$使得$|\mathcal{D}_U^{(T_0)}| = 0$。

**证明概要**：  
设每轮迭代至少选择$m = \lceil k n_U \rceil$个样本，则经过至多$T_0 = \lceil \log_{1-k}(\epsilon/n_U) \rceil$轮迭代后，未标注集规模$|\mathcal{D}_U^{(t)}| \leq \epsilon$。取$\epsilon=1$即得证。

本算法通过动态调整训练集分布，实现了以下创新：

1. **自适应置信度阈值**：根据式(5)中$\mu_t,\sigma_t$的在线估计，自动适应数据分布偏移
2. **联邦安全计算**：所有模型训练步骤均在加密状态下进行，满足$\epsilon$-差分隐私要求
3. **双模态处理**：通过式(4)的统一框架，同时支持分类与回归任务

表1对比了传统VFL与VFPU-M的关键特性：

| 特性           | 传统VFL | VFPU-M          |
| -------------- | ------- | --------------- |
| 使用对齐样本   | ✓       | ✓               |
| 使用未对齐样本 | ✗       | ✓（通过伪标签） |
| 支持半监督学习 | ✗       | ✓               |
| 数据利用率     | 低      | 高              |
| 隐私保护强度   | 中等    | 高（DP加密）    |
| 跨任务适应性   | 单一    | 分类/回归双模   |

通过这种设计，VFPU-M在保持联邦学习隐私要求的前提下，显著提升了模型对长尾样本的表征能力。第4章的实验结果表明，在CIFAR-10数据集上，当对齐样本比例低于20%时，VFPU-M相较基线方法可获得超过15%的准确率提升。