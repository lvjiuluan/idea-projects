"""
GAIN function.
Date: 2020/02/28
Reference: J. Yoon, J. Jordon, M. van der Schaar, "GAIN: Missing Data
           Imputation using Generative Adversarial Nets," ICML, 2018.
Paper Link: http://proceedings.mlr.press/v80/yoon18a/yoon18a.pdf
Contact: jsyoon0823@gmail.com

这个Python文件是实现了一种名为GAIN（Generative Adversarial Imputation Nets）的数据插补方法。这种方法是用于处理有缺失值的数据的。

GAIN是在2018年的一个机器学习会议（ICML）上提出的，由J. Yoon, J. Jordon, M. van der Schaar共同完成。这种方法的思想是使用生成对抗网络（GANs）来预测并填充数据中的缺失值。

GANs是一种深度学习模型，它由两个部分组成：一个生成器和一个判别器。生成器的任务是生成看起来像真实数据的假数据，而判别器的任务是区分生成器生成的假数据和真实数据。在GAIN中，生成器用于生成缺失的数据，而判别器用于判断这些生成的数据是否像真实的数据。

因此，这个Python文件的主要任务就是实现这种基于GANs的数据插补方法。
"""

# Necessary packages
# import tensorflow as tf
# IF USING TF 2 use following import to still use TF < 2.0 Functionalities
import os

import tensorflow.compat.v1 as tf

from VF.util.utils import normalization, xavier_init, sample_batch_index, uniform_sampler, binary_sampler, \
    renormalization, rounding

tf.disable_v2_behavior()


import numpy as np
from tqdm import tqdm


def gain(data_x, gain_parameters):
    """
    Impute missing values in data_x 填补data_x中的缺失值

    Args:
      - data_x: original data with missing values 具有缺失值的原始数据
      - gain_parameters: GAIN network parameters: GAIN网络参数
        - batch_size: Batch size
        - hint_rate: Hint rate
        - alpha: Hyperparameter
        - iterations: Iterations

    Returns:
      - imputed_data: imputed data 填补后数据
    """
    # Define mask matrix 定义掩模矩阵
    data_m = 1 - np.isnan(data_x)  # 创建掩模矩阵，标记缺失值位置

    # System parameters（系统参数）
    # 获取网络参数
    batch_size = gain_parameters['batch_size']
    hint_rate = gain_parameters['hint_rate']
    alpha = gain_parameters['alpha']
    iterations = gain_parameters['iterations']

    # Data and dimension parameters（数据集和维度参数）
    no, dim = data_x.shape

    # Network architecture parameters（网络架构参数）
    h_dim = int(dim)  # 隐藏单元维度

    # Normalization 数据归一化处理
    norm_data, norm_parameters = normalization(data_x)  # 归一化，在utils中已定义好
    norm_data_x = np.nan_to_num(norm_data, 0)  # nan值处理为0
    # data_x为原始数据，norm_data为归一化后的数据，norm_parameters为每一列的最大值数组, 最小值数组这里叫做参数也没错，方便后续逆归一化

    # GAIN architecture（GAIN网络架构）
    # Input placeholders
    # tf.placeholder()简单理解为占位符
    # Data vector
    X = tf.placeholder(tf.float32, shape=[None, dim])
    # Mask vector
    M = tf.placeholder(tf.float32, shape=[None, dim])
    # Hint vector
    H = tf.placeholder(tf.float32, shape=[None, dim])

    # Discriminator variables 判别器网络变量
    # D_W1, D_b1, D_W2, D_b2, D_W3, 和 D_b3 是判别器的权重和偏置变量。在 GAIN 模型中，判别器用于评估生成的数据以及数据的提示信息（hint）是否合理。
    # tf.Variable(xavier_init([dim * 2, h_dim])) 创建一个权重变量 D_W1，它的形状是 [dim * 2, h_dim]，并使用 xavier_init 函数来对其进行初始化。
    # dim * 2 表示输入的维度是数据和提示信息的拼接。这个权重用于第一层的神经网络。
    D_W1 = tf.Variable(xavier_init([dim * 2, h_dim]))  # Data + Hint as inputs
    D_b1 = tf.Variable(tf.zeros(shape=[h_dim]))

    D_W2 = tf.Variable(xavier_init([h_dim, h_dim]))
    D_b2 = tf.Variable(tf.zeros(shape=[h_dim]))

    D_W3 = tf.Variable(xavier_init([h_dim, dim]))
    D_b3 = tf.Variable(tf.zeros(shape=[dim]))  # Multi-variate outputs

    theta_D = [D_W1, D_W2, D_W3, D_b1, D_b2, D_b3]

    # Generator variables #生成器网络变量
    # Data + Mask as inputs (Random noise is in missing components)
    G_W1 = tf.Variable(xavier_init([dim * 2, h_dim]))
    G_b1 = tf.Variable(tf.zeros(shape=[h_dim]))

    G_W2 = tf.Variable(xavier_init([h_dim, h_dim]))
    G_b2 = tf.Variable(tf.zeros(shape=[h_dim]))

    G_W3 = tf.Variable(xavier_init([h_dim, dim]))
    G_b3 = tf.Variable(tf.zeros(shape=[dim]))

    theta_G = [G_W1, G_W2, G_W3, G_b1, G_b2, G_b3]

    # GAIN functions
    # 生成器Generator
    def generator(x, m):
        # Concatenate Mask and Data 数据集和掩码矩阵拼接
        inputs = tf.concat(values=[x, m], axis=1)
        G_h1 = tf.nn.relu(tf.matmul(inputs, G_W1) + G_b1)
        G_h2 = tf.nn.relu(tf.matmul(G_h1, G_W2) + G_b2)
        # MinMax normalized output
        G_prob = tf.nn.sigmoid(tf.matmul(G_h2, G_W3) + G_b3)
        return G_prob

    """
    tf.concat向量拼接axis的意义
    # tensor t3 with shape [2, 3]
    # tensor t4 with shape [2, 3]
    tf.shape(tf.concat([t3, t4], axis = 0))  # [4, 3]
    tf.shape(tf.concat([t3, t4], axis = 1))  # [2, 6]
    """

    # 判别器Discriminator
    def discriminator(x, h):
        # Concatenate Data and Hint 数据集和提示hint矩阵拼接
        inputs = tf.concat(values=[x, h], axis=1)
        D_h1 = tf.nn.relu(tf.matmul(inputs, D_W1) + D_b1)
        D_h2 = tf.nn.relu(tf.matmul(D_h1, D_W2) + D_b2)
        D_logit = tf.matmul(D_h2, D_W3) + D_b3
        D_prob = tf.nn.sigmoid(D_logit)
        return D_prob

    """
    这个generator生成器和discriminate判别器的结构完全一样，只有参数不同,至于为什么不同
    hint揭示了原始数据中缺失部分的某些信息，其实和mask作用完全一样，
    但是discriminator判别器的hint的最终目的是逼近generator生成器的mask掩码矩阵，肯定不能直接把答案放到discriminator判别器网络里
    """

    # GAIN structure GAIN模型搭建
    # Generator 生成数据
    G_sample = generator(X, M)

    # Combine with observed data（与观察数据合并）
    Hat_X = X * M + G_sample * (1 - M)  # Hat_X为原始数据X的未缺失部分与生成网络生成的数据G_sample的缺失部分的合并 (合并观察到的数据和生成的数据)

    # Discriminator
    D_prob = discriminator(Hat_X, H)

    # GAIN loss（GAIN损失函数）
    # D_loss and G_loss（判别器和生成器的损失）
    # D的损失函数就是判别器输出数据和M的差值的平均
    D_loss_temp = -tf.reduce_mean(M * tf.log(D_prob + 1e-8) + (1 - M) * tf.log(1. - D_prob + 1e-8))

    # G的损失函数包括两部分:D生成的缺失部分数据的判定损失、生成的未缺失值数据和真实的未缺失值数据的MSE的融合
    G_loss_temp = -tf.reduce_mean((1 - M) * tf.log(D_prob + 1e-8))
    # MSE loss (Mean Squared Error)（均方误差损失）
    MSE_loss = tf.reduce_mean((M * X - M * G_sample) ** 2) / tf.reduce_mean(M)

    D_loss = D_loss_temp
    # Combined loss for generator（生成器的综合损失）
    G_loss = G_loss_temp + alpha * MSE_loss
    """   
    注：
    tf.log(y) 计算y的自然对数,如tf.log(1)=0
    tf.reduce_mea函数用于计算张量tensor沿着指定的数轴（tensor的某一维度）上的平均值，主要用作降维或者计算tensor的平均值
    如：tf.reduce_mean( input_tensor, axis=None, keep_dims=False, name=None, reduction_indices=None )
    input_tensor： 输入的待降维的tensor
    axis： 指定的轴，如果不指定，则计算所有元素的均值
    keep_dims：是否保持维度，默认False。设置为True，输出的结果保持输入tensor的形状，设置为False，输出结果会降低维度
    name： 操作的名称
    reduction_indices：在以前版本中用来指定轴，已弃用
    """
    # Model training（模型训练）
    # GAIN solver（GAIN优化器）
    D_solver = tf.train.AdamOptimizer().minimize(D_loss, var_list=theta_D)
    G_solver = tf.train.AdamOptimizer().minimize(G_loss, var_list=theta_G)
    # 参数优化器AdamOptimizer,用来做学习率优化的
    # Iterations
    sess = tf.Session()
    sess.run(tf.global_variables_initializer())
    # sess.run(tf.global_variables_initializer())在会话执行这个之后,之前的palceholder还有variable现在才初始化变量

    # Start Iterations 开始迭代训练
    for it in tqdm(range(iterations)):
        # Sample batch 小批次提取数据
        batch_idx = sample_batch_index(no, batch_size)
        X_mb = norm_data_x[batch_idx, :]
        M_mb = data_m[batch_idx, :]
        # Sample random vectors
        Z_mb = uniform_sampler(0, 0.01, batch_size, dim)
        # Sample hint vectors
        H_mb_temp = binary_sampler(hint_rate, batch_size, dim)
        H_mb = M_mb * H_mb_temp

        # Combine random vectors with observed vectors
        X_mb = M_mb * X_mb + (1 - M_mb) * Z_mb

        _, D_loss_curr = sess.run([D_solver, D_loss_temp], feed_dict={M: M_mb, X: X_mb, H: H_mb})
        _, G_loss_curr, MSE_loss_curr = sess.run([G_solver, G_loss_temp, MSE_loss],
                                                 feed_dict={X: X_mb, M: M_mb, H: H_mb})

        """ 
        注:
        这里的hint_rate照常理来说应该是1,但是这里用90%估计是个正则化设计,和dropout类似防过拟合的
        生成噪声数据Z_mb填补在缺失处
        利用训练好的生成器生成现在已经可以以假乱真的数据
        将生成的缺失部分的数据填补进去
        sample_batch_index在utils封装好了:
        """

    # Return imputed data（返回填补后的数据）
    Z_mb = uniform_sampler(0, 0.01, no, dim)
    M_mb = data_m
    X_mb = norm_data_x
    X_mb = M_mb * X_mb + (1 - M_mb) * Z_mb

    imputed_data = sess.run([G_sample], feed_dict={X: X_mb, M: M_mb})[0]

    imputed_data = data_m * norm_data_x + (1 - data_m) * imputed_data

    # Renormalization 逆归一化
    imputed_data = renormalization(imputed_data, norm_parameters)

    # Rounding
    imputed_data = rounding(imputed_data, data_x)

    return imputed_data
