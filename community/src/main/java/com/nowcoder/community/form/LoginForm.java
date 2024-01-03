package com.nowcoder.community.form;

import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private String validCode;
    private Boolean rememberMe;
}
