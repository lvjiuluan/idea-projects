import numpy as np
import pandas as pd

# 第一种数据生成
"""
按照缺失率的大小将缺失行的值设为nan
返回原始数据，缺失后数据，掩码矩阵
"""


def data_loader1(data_name, miss_rate):
    file_name = 'datasetsAB/' + data_name + '.csv'
    complete_data = pd.read_csv(file_name, sep=',')
    data_m = complete_data.copy()  # 掩码矩阵
    no, dim = complete_data.shape
    n = round(no * miss_rate)  # 根据缺失率得到舍弃的行数
    # 生成一个随机索引数组
    random_indices = np.random.choice(no, size=n, replace=False)
    # 将选中舍弃的行置为0，剩余保留行置为1
    data_m.loc[random_indices, :] = 0
    data_m.loc[~complete_data.index.isin(random_indices), :] = 1
    # incomplete_data = complete_data.drop(complete_data.sample(n).index)
    # np.random.shuffle(incomplete_data)

    incomplete_data = complete_data.copy()
    incomplete_data[data_m == 0] = np.nan

    return complete_data, incomplete_data, data_m


# 第二种数据生成
"""
按照缺失率的大小将部分行丢弃
返回原始数据，缺失后数据，掩码矩阵
"""


def data_loader2(data_name, miss_rate):
    file_name = 'datasetsAB/' + data_name + '.csv'
    complete_data = pd.read_csv(file_name, sep=',')
    no, dim = complete_data.shape
    n = round(no * miss_rate)  # 根据缺失率得到舍弃的行数
    # 随机将行数丢弃
    incomplete_data = complete_data.drop(complete_data.sample(n).index)
    # incomplete_data = complete_data.drop(complete_data.sample(n).index)
    # np.random.shuffle(incomplete_data)

    return complete_data, incomplete_data
