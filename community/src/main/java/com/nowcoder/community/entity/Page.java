package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    /*
     * 封装分页信息，尽量保持一致
     * */
    // 当前页
    private Integer pageNum = 1;
    // 每页显示多少条
    private Integer pageSize = 10;
    // 数据有多少行，用于计算显示多少页
    private Integer rows;
    // 查询路径 用于复用分页链接
    private String path;

    // 获取起始行
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

    // 获取总页数
    public Integer getTotal() {
        return Integer.valueOf((int) Math.ceil(rows / (pageSize * 1.0)));
    }

    // 获取起始页
    public Integer getFrom() {
        return pageNum - 2 < 1 ? 1 : pageNum - 2;
    }

    // 获取终止页
    public Integer getTo() {
        return pageNum + 2 > getTotal() ? getTotal() : pageNum + 2;
    }
}