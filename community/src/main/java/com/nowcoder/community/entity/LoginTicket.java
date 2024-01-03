package com.nowcoder.community.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket {
    private Integer id;

    private Integer userId;

    private String ticket;

    private Integer status;

    private Date expired;
}