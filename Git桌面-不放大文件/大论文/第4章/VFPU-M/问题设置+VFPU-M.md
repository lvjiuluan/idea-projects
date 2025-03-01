# 1 问题设置

考虑一个两方纵向联邦学习（Vertical Federated Learning, VFL）的场景，这是参考文献[40]中定义的典型 VFL 设置。参与方包括 Party A 和 Party B，其中只有一方拥有标签。

首先，Party A 拥有数据集：

$$
\mathcal{D}^A := \{X^A_i\}_{i=1}^{n^A}
$$

其中，$X^A_i$ 是第 $i$ 个样本的特征向量，$n^A$ 是样本数量。Party A 的数据仅包含特征，不包含标签。

接着，Party B 拥有数据集：

$$
\mathcal{D}^B := \{(X^B_i, Y^B_i)\}_{i=1}^{n^B}
$$

其中，$X^B_i$ 是第 $i$ 个样本的特征向量，$Y^B_i \in \{0,1\}^C$ 是对应的独热编码（one-hot encoding）真实标签，$C$ 表示类别数，$n^B$ 是样本数量。Party B 拥有标签，这在 VFL 中至关重要，因为标签通常用于监督学习任务。然而，Party B 缺乏足够的特征来单独构建一个准确的模型，因此需要利用 Party A 提供的补充特征。

需要强调的是，$\mathcal{D}^A$ 和 $\mathcal{D}^B$ 分别由 Party A 和 Party B 私有保存，双方不能互相暴露其数据集。

在 VFL 中，Party A 和 Party B 的数据集 $\mathcal{D}^A$ 和 $\mathcal{D}^B$ 包含了不同样本的特征。为了进行联合学习，需要将具有相同身份的样本对齐。假设通过隐私保护的加密实体匹配技术[30]，双方已经完成了样本对齐，得到了对齐样本集（$al=align$）：

$$
\mathcal{D}_{al} := \{X^A_{i_{al}}, X^B_{i_{al}}, Y^B_{i_{al}}\}_{i=1}^{n_{al}}
$$

其中，$n_{al}$ 是对齐样本的数量。Party A 拥有对齐样本的特征：

$$
\mathcal{D}^A_{al} := \{X^A_{i_{al}}\}_{i=1}^{n_{al}}
$$

Party B 拥有对齐样本的特征和标签：
$$
\mathcal{D}^B_{al} := \{X^B_{i_{al}}, Y^B_{i_{al}}\}_{i=1}^{n_{al}}
$$

如果将 $\mathcal{D}^A$ 和 $\mathcal{D}^B$ 连接起来，并使具有相同身份的样本对齐，我们将得到一个如图 1.b 所示的单一数据集。这个数据集是垂直分割的，每个方拥有该数据集的一个垂直分区（或部分视图），这正是“纵向联邦学习”一词的由来。然而，两方之间通常只存在有限数量的对齐样本。

除了对齐样本外，每个方还拥有一些非对齐样本，即没有来自另一方对应样本的数据。对于 Party A，非对齐样本集（$al=not align$）表示为：

$$
\mathcal{D}^A_{nl} := \{X^A_{i_{nl}}\}_{i=1}^{n^A_{nl}}
$$

对于 Party B，非对齐样本表示为：

$$
\mathcal{D}^B_{nl} := \{X^B_{i_{nl}}, Y^B_{i_{nl}}\}_{i=1}^{n^B_{nl}}
$$

从单一表格数据集（见图 1.b）的角度来看，每个方对于另一方的非对齐样本都没有对应的特征（或标签）。我们将这些特征（或标签）视为“缺失”。

传统的 VFL 方法仅使用对齐样本 $\mathcal{D}_{al}$ 来构建联邦机器学习模型，而将非对齐样本 $\mathcal{D}^A_{nl}$ 和 $\mathcal{D}^B_{nl}$ 弃置不用。这种做法在对齐样本数量较少时，可能会限制模型的性能，因为大量潜在有用的数据被忽略。

本文提出了一种新的方法 VFPU-M-Syn，旨在充分利用非对齐样本 $\mathcal{D}^A_{nl}$ 和 $\mathcal{D}^B_{nl}$，以提升纵向联邦学习（VFL）模型的性能。该方法结合了 纵向联邦半监督学习 和 表格数据生成技术，通过将对齐样本 $\mathcal{D}_{al}$ 视为有标签数据，其中 $X^A_{al}$ 的“标签”可看作 $X^B_{al}$ 的特征值（见图1.a），而将非对齐样本 $\mathcal{D}^A_{nl}$ 视为无标签数据，利用半监督学习从对齐样本中学习以增强模型的泛化能力，同时采用表格数据生成技术填补与 Party A 相关性较弱的特征缺失值，并与纵向联邦学习相结合优化数据补全。相比传统 VFL 方法，VFPU-M-Syn 不仅利用了对齐样本 $\mathcal{D}_{al}$，还充分利用了非对齐样本 $\mathcal{D}^A_{nl}$ 和 $\mathcal{D}^B_{nl}$，显著提高了数据利用率；通过纵向联邦半监督学习，模型能从无标签数据中提取有用信息，进一步提升泛化能力；而表格数据生成技术的引入则使得缺失特征的填补更加合理，从而优化了数据填补策略并提高了模型整体性能。总之，VFPU-M-Syn 在传统 VFL 框架基础上引入创新技术，充分利用非对齐样本，在对齐样本有限的情况下显著提升了 VFL 模型的准确性和泛化能力，实验结果也验证了其优越性。





# 2 方法描述

$X_{al}^A$ ：A方对齐部分数据

$X_{al}^B$： B方对齐部分数据

$X_{nl}^A$：A方未对齐部分数据

$X_{nl}^B$：B方未对齐部分数据

从相关性排序列表$\mathcal{L}_B^{sorted}$中按顺序取出B方对齐部分的一部分特征列，进行预测，由于$\mathcal{L}_B^{sorted}$是有序的，所以越在之前，该特征列与A方之间的相关性越强，对于$\mathcal{L}_B^{sorted}$中第$t$个数据：
$$
(u_{t},\varphi_t^B),t = 1,...,{m_B}
$$
$u_{t}$表示B方第$t$个特征列与A方之间的相关性，$\varphi_t^B$为B方对齐部分的第$t$个特征列，$ \varphi^B_t = [\varphi^B_{t1}, \varphi^B_{t2}, ..., \varphi^B_{qn_{tl}}] $ 表示该特征在所有对齐样本上的取值，$ n_{al} $ 为对齐样本的数量。将$f_t^B$视作的对齐数据的标签列，即有标签数据，未对齐数据看作无标签数据，则可以构造下面的文献[1]中提到的纵向联邦半监督学习的场景:
$$
\eqalign{
  & {D^{Label}} = \{ X_{al}^A,X_{al}^B,\varphi_t^B\}   \cr 
  & {D^{Unlabel}} = {\rm{\{ X}}_{nl}^A,X_{nl}^B{\rm{\} }} \cr}
$$


将${D^{Label}}$与${D^{Unlabel}}$ 作为输入，输入到VFPU-M算法中，该算法的代码实现如下，仔细阅读并理解代码，根据代码和上下文数学 符合设置，用严谨的数学语言将代码转换为VFPU-M算法，然后用学术论文风格的语言将该论文的章节补充完整，描述好算法，要求科学正确

$X_{al}^A$ ：对应XA_L

$X_{al}^B$： 对应XB_L

$X_{nl}^A$：对应XA_U

$X_{nl}^B$：对应XB_U

$\varphi_t^B$：对应y_L



```python
def __fit_train(self, XA_L, XB_L, y_L, XA_U, XB_U, task_type):
    """
    自训练过程的通用函数，可用于分类和回归任务。

    参数:
    ----------
    XA_L : np.ndarray
        已标注数据的 A 方特征，形状为 (n_L, dimA)。
    XB_L : np.ndarray
        已标注数据的 B 方特征，形状为 (n_L, dimB)。
    y_L : np.ndarray
        已标注数据的标签，形状为 (n_L,)。
    XA_U : np.ndarray
        未标注数据的 A 方特征，形状为 (n_U, dimA)。
    XB_U : np.ndarray
        未标注数据的 B 方特征，形状为 (n_U, dimB)。
    task_type : str
        任务类型，'clf'表示分类任务，'reg'表示回归任务。

    返回:
    ----------
    None
        函数执行结束后，会将对未标注数据的预测结果写入相应的self.pred_clf或self.pred_reg。
    """
    # 根据任务类型确定使用的模型、参数和结果存储变量
    is_clf_task = task_type == TaskType.CLASSIFICATION.value
    model = self.clf if is_clf_task else self.reg
    k_value = self.k1 if is_clf_task else self.k2
    pick_lowest = not is_clf_task  # 分类任务选最高置信度，回归任务选最低Z分数
    
    # 初始化预测结果数组, 与 XA_U, XB_U 行数一致
    unlabeled_indices = np.arange(len(XA_U))
    pred_result = np.full(len(XA_U), np.nan)
    
    # 存储到相应的预测结果变量
    if is_clf_task:
        self.pred_clf = pred_result
    else:
        self.pred_reg = pred_result
    
    self.logger.info("[INIT] 初始化预测结果, 未标注数据数量=%d", len(XA_U))
    self.logger.info("[INIT] 最大迭代次数 max_iter=%d, 每轮选取比例 k=%.2f", 
                     self.max_iter, k_value)

    start_time = time.time()
    for epoch in range(self.max_iter):
        epoch_start_time = time.time()

        self.logger.info("===== [Epoch %d/%d] 迭代开始 =====", epoch + 1, self.max_iter)
        self.logger.info("[INFO] 当前 Labeled 样本数量: %d, Unlabeled 样本数量: %d",
                         len(XA_L), len(XA_U))

        # 若当前没有任何有标签数据，无法进行训练，直接结束
        if len(XA_L) == 0:
            self.logger.warning("[WARNING] Labeled 数据量为0，无法继续训练，终止迭代。")
            break

        # 1. 训练模型
        task_name = "分类器" if is_clf_task else "回归器"
        self.logger.info("[STEP 1] 开始训练%s (基于 %d 个 Labeled 样本)...", task_name, len(XA_L))
        model_start_time = time.time()
        model.fit(XA_L, XB_L, y_L)
        model_time = time.time() - model_start_time
        self.logger.info("[STEP 1] %s训练完成，耗时 %.2f 秒。", task_name, model_time)

        # 对分类任务输出一些额外信息
        if is_clf_task and hasattr(model, 'classes_'):
            self.logger.debug("[DEBUG] 分类器识别的类别列表: %s", model.classes_)
        
        # 2. 对 Unlabeled 数据打分
        self.logger.info("[STEP 2] 对 Unlabeled 数据进行置信度计算...")
        scores_start_time = time.time()
        
        # 根据任务类型计算置信度分数
        if is_clf_task:
            pred = model.predict(XA_U, XB_U)  # 预测数据的标签
            proba = model.get_predict_proba()  # 此次预测数据的概率值
            scores = proba.max(axis=1)  # 取预测概率中最大的值作为该样本的置信度
        else:
            predictions = model.predict(XA_U, XB_U)
            mean_prediction = np.mean(predictions)
            std_prediction = np.std(predictions)
            # 计算每个预测值的 Z 分数（标准化偏离程度）
            scores = np.abs((predictions - mean_prediction) / std_prediction)
            pred = predictions  # 为了统一后续代码，回归任务中也使用pred变量
            
        scores_time = time.time() - scores_start_time
        self.logger.info("[STEP 2] 置信度计算完成，耗时 %.2f 秒。", scores_time)
        self.logger.debug("[DEBUG] 置信度分数统计: min=%.4f, max=%.4f, mean=%.4f, std=%.4f",
                          scores.min(), scores.max(), scores.mean(), scores.std())

        # 3. 选出符合条件的那部分数据
        self.logger.info("[STEP 3] 按置信度选取前 %.2f%% 的未标注样本 (min_confidence=%.2f)...",
                         k_value * 100, self.min_confidence)
        idx = get_top_k_percent_idx(scores, k_value, pick_lowest=pick_lowest)

        if len(idx) == 0:
            self.logger.info("[INFO] 本轮没有样本满足置信度筛选条件，终止迭代。")
            break

        self.logger.info("[STEP 3] 本轮选中高置信度样本数量: %d, 占未标注样本总数的 %.2f%%",
                         len(idx), 100.0 * len(idx) / (len(XA_U) + 1e-9))
        selected_original_idx = unlabeled_indices[idx]

        # 4. 得到这部分数据的预测标签
        self.logger.info("[STEP 4] 获取高置信度样本的预测标签。")
        best_pred = pred[idx]

        # 5. 将 pred_result 对应位置赋值为 best_pred
        if is_clf_task:
            self.pred_clf[selected_original_idx] = best_pred
        else:
            self.pred_reg[selected_original_idx] = best_pred

        # 6. 将这些样本移动到 Labeled 集合中
        self.logger.info("[STEP 5] 合并高置信度样本至 Labeled 集...")
        XA_L = np.concatenate([XA_L, XA_U[idx]], axis=0)
        XB_L = np.concatenate([XB_L, XB_U[idx]], axis=0)
        y_L = np.concatenate([y_L, best_pred], axis=0)
        self.logger.info("[STEP 5] 合并后 Labeled 样本数=%d", len(XA_L))

        # 从 Unlabeled 集中删除这些样本
        XA_U = np.delete(XA_U, idx, axis=0)
        XB_U = np.delete(XB_U, idx, axis=0)
        unlabeled_indices = np.delete(unlabeled_indices, idx, axis=0)
        self.logger.info("[STEP 5] 剩余 Unlabeled 样本数=%d", len(XA_U))

        # 判断是否满足提前收敛条件（本轮选样本不足某个数量）
        if len(idx) < self.convergence_threshold:
            self.logger.info("[INFO] 本轮新增标注数量=%d < 收敛阈值=%d，提前结束迭代。",
                             len(idx), self.convergence_threshold)
            break

        # 如果 Unlabeled 集已空，结束迭代
        if len(XA_U) == 0:
            self.logger.info("[INFO] Unlabeled 样本已全部处理完，提前结束迭代。")
            break

        epoch_elapsed_time = time.time() - epoch_start_time
        self.logger.info("===== [Epoch %d/%d] 迭代结束，耗时 %.2f 秒 =====",
                         epoch + 1, self.max_iter, epoch_elapsed_time)

    total_elapsed_time = time.time() - start_time
    self.logger.info("[DONE] 自训练流程结束，共进行了 %d 轮迭代，耗时 %.2f 秒。", epoch + 1, total_elapsed_time)
    self.logger.info("[DONE] 目前尚有 %d 个未标注样本未获得最终预测。", len(XA_U))

    # 若还有剩余的未标注数据，则进行一次最终预测
    if len(XA_U) != 0:
        self.logger.info("[FINAL] 对剩余 %d 个未标记样本进行最终预测...", len(XA_U))
        final_pred_start_time = time.time()
        final_pred = model.predict(XA_U, XB_U)
        final_pred_time = time.time() - final_pred_start_time
        self.logger.info("[FINAL] 最终预测完成，耗时 %.2f 秒。", final_pred_time)

        # 根据任务类型保存预测结果
        if is_clf_task:
            self.logger.info(f"final_pred = self.clf.predict(XA_U, XB_U) "
                         f"语句输入形状： XA_U.shape = {XA_U.shape},XB_U.shape = {XB_U.shape},"
                         f"\n输出形状： final_pred.shape = {final_pred.shape}")
            self.logger.info(f"final_pred的长度为{len(final_pred)}")
            self.logger.info(f"self.pred_clf[unlabeled_indices]的长度为: {len(self.pred_clf[unlabeled_indices])}")
            self.pred_clf[unlabeled_indices] = final_pred
        else:
            self.logger.debug("[DEBUG] 剩余未标注样本的预测标签分布: %s",
                          np.unique(final_pred, return_counts=True))
            self.pred_reg[unlabeled_indices] = final_pred

    self.logger.info("[DONE] 所有未标注样本的预测任务完成！(self.pred 已更新)")
```





