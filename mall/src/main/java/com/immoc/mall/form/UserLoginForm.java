package com.immoc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {
    //    @NotBlank  判断字符 空格也是非法的
    //    @NotEmpty 判断集合
    //    @NotNull
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
