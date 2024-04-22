"""
(1)binary_sampler 随机选取样本
(2)normalization 归一化
(3)oneHot one-hot编码
(4) renormalization: Recover the data from normalized data 逆归一化：从归一化数据中恢复数据
(5) rounding: Handle categorical variables after imputation
(6) rmse_loss: Evaluate imputed data in terms of RMSE 根据RMSE评估填补后地数据
(7) xavier_init: Xavier initialization 初始化
(8) binary_sampler: sample binary random variables 随机选取样本
(9) uniform_sampler: sample uniform random variables 对均匀随机变量进行采样
(10) sample_batch_index: sample random batch index 样本随机批次索引
"""

# Necessary packages
import numpy as np
# import tensorflow as tf
# IF USING TF 2 use following import to still use TF < 2.0 Functionalities
import pandas as pd
import tensorflow.compat.v1 as tf
from sklearn.preprocessing import OneHotEncoder

tf.disable_v2_behavior()


def binary_sampler(p, rows, cols):
    """
    生成二进制随机矩阵

    Args:
      - p: 元素为1的概率
      - rows: 生成的矩阵的行数
      - cols: 生成的矩阵的列数

    Returns:
      - 二进制随机矩阵.
    """
    unif_random_matrix = np.random.uniform(0., 1., size=[rows, cols])  # 生成一个均匀分布的随机矩阵
    binary_random_matrix = 1 * (unif_random_matrix < p)  #
    return binary_random_matrix  # 掩码矩阵


# 归一化
def normalization(data, parameters=None):
    """
    Normalize data in [0, 1] range.

    Args:
      - data: original data

    Returns:
      - norm_data: normalized data
      - norm_parameters: min_val, max_val for each feature for renormalization
    """

    # Parameters
    _, dim = data.shape
    norm_data = data.copy()

    if parameters is None:

        # MixMax normalization
        min_val = np.zeros(dim)
        max_val = np.zeros(dim)

        # For each dimension
        for i in range(dim):
            min_val[i] = np.nanmin(norm_data[:, i])
            norm_data[:, i] = norm_data[:, i] - np.nanmin(norm_data[:, i])
            max_val[i] = np.nanmax(norm_data[:, i])
            norm_data[:, i] = norm_data[:, i] / (np.nanmax(norm_data[:, i]) + 1e-6)

            # Return norm_parameters for renormalization
        norm_parameters = {'min_val': min_val,
                           'max_val': max_val}

    else:
        min_val = parameters['min_val']
        max_val = parameters['max_val']

        # For each dimension
        for i in range(dim):
            norm_data[:, i] = norm_data[:, i] - min_val[i]
            norm_data[:, i] = norm_data[:, i] / (max_val[i] + 1e-6)

        norm_parameters = parameters

    return norm_data, norm_parameters


# one-hot编码
def oneHot(data):
    # 分离出连续列和分类列
    categorical = data.select_dtypes(include=['object'])
    # 选择除分类列之外的所有列
    numerical = data.select_dtypes(exclude=['object'])
    # 创建一个OneHotEncoder()对象
    encoder = OneHotEncoder()
    # 转换数据框为NumPy数组并进行特征编码
    df_categorical_encoded = encoder.fit_transform(categorical.values)
    # 转换为数据框
    df_categorical_encoded = pd.DataFrame(df_categorical_encoded.toarray(), columns=encoder.get_feature_names_out())
    df_final_encoded = pd.concat([numerical, df_categorical_encoded], axis=1)
    return df_final_encoded


def renormalization(norm_data, norm_parameters):
    """
    Renormalize data from [0, 1] range to the original range.

    Args:
      - norm_data: normalized data
      - norm_parameters: min_val, max_val for each feature for renormalization

    Returns:
      - renorm_data: renormalized original data
    """

    min_val = norm_parameters['min_val']
    max_val = norm_parameters['max_val']

    _, dim = norm_data.shape
    renorm_data = norm_data.copy()

    for i in range(dim):
        renorm_data[:, i] = renorm_data[:, i] * (max_val[i] + 1e-6)
        renorm_data[:, i] = renorm_data[:, i] + min_val[i]

    return renorm_data


def rounding(imputed_data, data_x):
    """Round imputed data for categorical variables.

    Args:
      - imputed_data: imputed data
      - data_x: original data with missing values

    Returns:
      - rounded_data: rounded imputed data
    """

    _, dim = data_x.shape
    rounded_data = imputed_data.copy()

    for i in range(dim):
        temp = data_x[~np.isnan(data_x[:, i]), i]
        # Only for the categorical variable
        if len(np.unique(temp)) < 20:
            rounded_data[:, i] = np.round(rounded_data[:, i])

    return rounded_data


def rmse_loss(ori_data, imputed_data, data_m):
    """Compute RMSE loss between ori_data and imputed_data

    Args:
      - ori_data: original data without missing values
      - imputed_data: imputed data
      - data_m: indicator matrix for missingness

    Returns:
      - rmse: Root Mean Squared Error
    """

    ori_data, norm_parameters = normalization(ori_data)
    imputed_data, _ = normalization(imputed_data, norm_parameters)

    # Only for missing values
    nominator = np.sum(((1 - data_m) * ori_data - (1 - data_m) * imputed_data) ** 2)
    denominator = np.sum(1 - data_m)

    rmse = np.sqrt(nominator / float(denominator))

    return rmse


def xavier_init(size):
    """Xavier initialization.

    Args:
      - size: vector size

    Returns:
      - initialized random vector.
    """
    in_dim = size[0]
    xavier_stddev = 1. / tf.sqrt(in_dim / 2.)
    return tf.random_normal(shape=size, stddev=xavier_stddev)


def binary_sampler(p, rows, cols):
    """
    Sample binary random variables.

    Args:
      - p: probability of 1
      - rows: the number of rows
      - cols: the number of columns

    Returns:
      - binary_random_matrix: generated binary random matrix.
    """
    unif_random_matrix = np.random.uniform(0., 1., size=[rows, cols])
    binary_random_matrix = 1 * (unif_random_matrix < p)
    return binary_random_matrix  # 掩码矩阵


def uniform_sampler(low, high, rows, cols):
    """Sample uniform random variables.

    Args:
      - low: low limit
      - high: high limit
      - rows: the number of rows
      - cols: the number of columns

    Returns:
      - uniform_random_matrix: generated uniform random matrix.
    """
    return np.random.uniform(low, high, size=[rows, cols])


def sample_batch_index(total, batch_size):
    """Sample index of the mini-batch.

    Args:
      - total: total number of samples
      - batch_size: batch size

    Returns:
      - batch_idx: batch index
    """
    total_idx = np.random.permutation(total)
    batch_idx = total_idx[:batch_size]
    return batch_idx
