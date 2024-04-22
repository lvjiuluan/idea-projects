# 目前出现运行计算计算Cramér系数，还可选择计算pearson，spearman，kendall系数的相关性


import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
from scipy.stats import chi2_contingency

if __name__ == '__main__':
    path = "./bank+marketing/bank-additional/bank-additional/bank-additional-full.csv"
    # path="./bank+marketing/bank/bank-full.csv"
    # 加载数据
    raw_data = pd.read_csv(path, sep=";")
    print("数据信息------------------------")
    print(raw_data.info())
    y_pre = raw_data.pop('y')
    print('预测信息--------------------------')
    print(y_pre)
    number_list = ["age", "duration", "pdays", "previous", "emp.var.rate", "cons.price.idx", "cons.conf.idx"
        , "euribor3m", "nr.employed"]  # bank-addtional-full 数值型特征列
    categorical_list = ["job", "marital", "education", "default", "housing", "loan", "month", "month", "poutcome",
                        "day_of_week"]  # 类别型数据

    pearson_numic = ["duration", "campaign", "pdays", "previous", "emp.var.rate",  # Pearson计算的列
                     "cons.price.idx", "cons.conf.idx", "euribor3m", "nr.employed"]
    #  对缺失值用众数填充
    raw_data.replace('unknown', np.nan, inplace=True)
    mode = raw_data.mode().iloc[0]
    raw_data.fillna(mode, inplace=True)


    # 对类别型数据进行one-hot编码
    #  encode_data=pd.get_dummies(raw_data,columns=categorical_lsit)

    # 采用标签编码
    #  创建LabelEncoder对象
    #  le = LabelEncoder()
    #  encode_data=raw_data
    #  encode_data.loc[:,categorical_lsit]=encode_data.loc[:,categorical_lsit].apply(le.fit_transform)
    #  # print(encode_data)
    #  print("one-hot编码后----------------------")
    #  print(encode_data.info())

    # 一：计算Cramér's V系数
    def cramers_v(confusion_matrix):
        chi2 = chi2_contingency(confusion_matrix)[0]
        n = confusion_matrix.sum().sum()
        phi2 = chi2 / n
        r, k = confusion_matrix.shape
        phi2corr = max(0, phi2 - ((k - 1) * (r - 1)) / (n - 1))
        rcorr = r - ((r - 1) ** 2) / (n - 1)
        kcorr = k - ((k - 1) ** 2) / (n - 1)
        return np.sqrt(phi2corr / min((kcorr - 1), (rcorr - 1)))


    # 创建空的结果列表
    result = [[] for _ in range(len(categorical_list))]

    # 计算特征之间的Cramér's V系数
    for i in range(len(categorical_list)):
        for j in range(len(categorical_list)):
            if i != j:
                # 创建交叉表
                cross_tab = pd.crosstab(raw_data[categorical_list[i]], raw_data[categorical_list[j]])
                # 计算Cramér's V系数
                cramers_v_score = cramers_v(cross_tab.values)
                result[i].append(cramers_v_score)
            else:
                result[i].append(1)

    # 打印结果
    for row in result:
        for value in row:
            print(value, end=' ')
        print()
    data = pd.DataFrame(result, columns=categorical_list, index=categorical_list)
    print(data.info)

    # 以下代码计算pearson，spearman，kendall系数的相关性
    # correlation_matrix = {}
    # for num_feature in number_list:
    #     correlation_matrix[num_feature] = {}
    #     for cat_feature in categorical_lsit:
    #         correlation = encode_data[num_feature].corr(encode_data[cat_feature],method='kendall')
    #         correlation_matrix[num_feature][cat_feature] = correlation

    # 计算编码后的数据的相关矩阵，以三行代表分别代表pearson，spearman，kendall系数的相关性，其余代码完全一样

    # correlation_matrix = pearson_data.corr()     #默认pearson
    # # correlation_matrix = pearson_data.corr(method='spearman') ['emp.var.rate']  #spearman
    # # correlation_matrix = pearson_data.corr(method='kendall') #kendall

    # # sort=correlation_matrix.sort_values(ascending=False)
    # correlation_df = pd.DataFrame(correlation_matrix)

    # 以下代码使用seaborn库绘制相关矩阵的热力图
    plt.figure(figsize=(15, 10))
    sns.heatmap(data, annot=True, cmap='coolwarm')
    plt.show()
    # # 将相关矩阵转换为Series对象，并按照降序排列
    # # correlation_series = correlation_matrix.unstack().sort_values(ascending=False)
    # # correlation_not_1=correlation_series.loc[correlation_series!=1]
    # print(correlation_matrix)

    # # 以下代码绘制散点图
    # sample_df = pearson_data.sample(n=100)  # 选择2个样本进行绘制
    # # plt.scatter(pearson_data['emp.var.rate'], pearson_data['euribor3m'],alpha=0.5)
    # plt.scatter(pearson_data['pdays'], pearson_data['previous'], color='red')  # 绘制选定的样本点
    # plt.xlabel('pdays')
    # plt.ylabel('previous')
    # plt.title('Scatter plot of pdays vs previous')
    # # plt.legend(['All Data', 'Selected Data'])  # 添加图例
    # plt.show()

    # correlation_not_1.to_csv("./bank-full/correlation_pearson_label.csv",sep=' ',encoding="utf-8")
    # # 打印前10个相关性最强的特征对
    # print(correlation_series)
