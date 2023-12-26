package com.immoc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {
    //    @NotBlank  判断字符 空格也是非法的
    //    @NotEmpty 判断集合
    //    @NotNull
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
