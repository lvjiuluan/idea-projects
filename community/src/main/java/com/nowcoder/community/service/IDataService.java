package com.nowcoder.community.service;

import java.util.Date;

// 数据统计
public interface IDataService {
    // 将指定的ip记录UV
    void recordUV(String ip);

    // 统计指定范围日期内的UV
    Long calculateUV(Date start, Date end);


    // 将指定用户记录DAU
    void recordDAU(Integer userId);

    // 统计指定范围日期内的DAU
    Long calculateDAU(Date start, Date end);
}
