好的，用户之前让我描述一个自训练方法的代码实现，分五个步骤。现在用户说之前的回复很好，但需要给每个步骤的标题加上英文名，其他不用改。我需要回顾之前的对话，确保准确添加英文名称。

首先，用户提到的五个步骤原来的中文标题是：
1. 监督模型训练：基于当前有标签数据的参数迭代更新
2. 无标签样本置信度评估：预测结果的可信度量化与排序
3. 高置信度样本选择：基于阈值与比例的动态筛选策略
4. 数据更新与伪标签注入：有标签集动态扩展与无标签池缩减
5. 动态终止条件校验：基于标注进度与数据平衡的收敛判断

用户现在希望每个标题后附上英文名。需要保持中文标题不变，然后在后面加上英文名称。比如，第一个步骤的标题变成“监督模型训练：基于当前有标签数据的参数迭代更新 (Supervised Model Training: Parameter Update with Labeled Data)”。

需要确保英文名称准确对应中文内容，同时简洁。比如第二个步骤关于置信度评估，英文可能用“Unlabeled Sample Confidence Evaluation”比较合适。注意每个步骤的英文名称需要保持一致性，比如使用动名词结构或者名词短语。

另外，用户提到“其它不用改了”，所以步骤的详细描述部分不需要调整，只需要在标题后添加英文名。需要检查每个步骤的英文名称是否正确，有没有重复或歧义。比如第五步的动态终止条件校验，英文可能用“Convergence Check”更简洁，但需要与步骤内容对应，如“Dynamic Termination Conditions: Convergence Check Based on Labeling Progress”。

最后，确保格式正确，每个标题后的英文名用括号括起来，使用合适的标点，比如中文标题后加空格和括号，英文名称首字母大写，保持专业术语的一致性。完成后，再通读一遍检查是否有遗漏或错误。


### 自训练方法（Self-Training）的五个核心步骤

1. 监督模型训练：基于当前有标签数据的参数迭代更新  
   (Supervised Model Training: Parameter Update with Labeled Data)  
   使用当前有标签数据集（`X_L`, `y_L`）进行监督学习训练模型（如分类器或回归器）。通过优化损失函数（如交叉熵、均方误差）更新模型参数，确保模型能够捕捉标签数据的分布模式。训练完成后，模型具备对无标签数据的初步预测能力。

2. 无标签样本置信度评估：预测结果的可信度量化与排序  
   (Unlabeled Sample Confidence Evaluation: Quantification and Ranking)  
   对无标签数据（`X_U`）进行预测，并基于任务类型计算每个样本的置信度分数：  
   - 分类任务：以预测概率最大值（`model.predict_proba`）衡量置信度，值越大表示预测越可靠。  
   - 回归任务：计算预测值与当前标签均值的标准化距离（`(预测值 - 均值)/标准差`），距离越小表示样本越接近分布中心，置信度越高。  

3. 高置信度样本选择：基于阈值与比例的动态筛选策略  
   (High-Confidence Sample Selection: Threshold-Based Filtering)  
   根据置信度分数选择前 `k%` 的样本（`get_top_k_percent_idx` 函数实现）：  
   - 分类任务：保留置信度高于 `min_confidence` 的样本，避免低质量伪标签注入。  
   - 回归任务：选择偏离均值最小的样本，确保伪标签的稳定性。筛选结果通过索引操作提取高置信度样本及其原始位置。

4. 数据更新与伪标签注入：有标签集动态扩展与无标签池缩减  
   (Data Update & Pseudo-Label Injection: Labeled Set Expansion)  
   将筛选出的高置信度样本及其预测标签（伪标签）合并到有标签数据集：  
   - 更新有标签数据：`X_L = np.concatenate([X_L, X_U_selected])`，`y_L` 同步扩展。  
   - 更新预测数组 `pred_array`，记录已选样本的最终预测值。  
   - 从未标签池 `X_U` 中移除已选样本，缩小后续迭代的数据规模。

5. 动态终止条件校验：基于标注进度与数据平衡的收敛判断  
   (Dynamic Termination Check: Convergence Based on Progress)  
   检查迭代终止条件，避免无效计算或过拟合：  
   - 新增样本不足：本轮新增样本数 `< convergence_threshold`（默认值可设为 `5`）。  
   - 无标签数据耗尽：`X_U` 为空时立即终止。  
   - 标签比例失衡：有标签/无标签数据比例超过 `max_labeled_unlabeled_ratio`（如 `10:1`）。  
   - 迭代次数限制：达到预设的 `max_iter` 最大轮次。  
   满足任一条件即终止流程，否则进入下一轮迭代。

### 补充步骤（最终预测）  
(Final Prediction for Remaining Unlabeled Data)  
对剩余无标签样本进行终轮预测，确保所有数据均有预测结果。此步骤避免因迭代终止导致的未标注数据遗漏，并填充 `pred_array` 中的缺失值（如回归任务中的 `NaN`）。





(1) Train the vertical federated model using aligned data, updating the model parameters through supervised learning to adapt to the distribution of the existing labeled data.
(2) Predict the missing data and calculate the confidence score for each sample.
(3) Select high-confidence samples based on the computed confidence scores.
(4) Perform data updates and inject pseudo-labels into the model.
(5) Check for iteration termination conditions to determine whether further model optimization is needed.





Sure! Here's a more concise version:

1. Train the vertical federated model with aligned data, updating parameters via supervised learning to fit the labeled data distribution.
2. Predict missing data and calculate the confidence score for each sample.
3. Select high-confidence samples based on the confidence scores.
4. Update the data and inject pseudo-labels into the model.
5. Check termination conditions to decide whether to continue optimization.

This version shortens each point without losing the key information.



(1) train vertical federated model with aligned data,
(2) predict for missing features and calculate  confidence score
(3) High-Confidence Sample Selection
(4) Data Update & Pseudo-Label Injection
(5) Convergence Check



 vertical federated model prediction for missing features

```
(1) Vertical Federated Model Training with Aligned Data  
(2) Missing Feature Prediction & Confidence Score Calculation  
(3) High-Confidence Sample Selection  
(4) Data Update & Pseudo-Label Injection  
(5) Convergence Check Based on Progress

 vertical federated model prediction for missing features
```

```
(1) base estimator training with aligned data  
(2) base estimator prediction for missing features  
(3) high-confidence sample selection  
(4) data update & pseudo-label injection  
(5) convergence check based on progress  
```

