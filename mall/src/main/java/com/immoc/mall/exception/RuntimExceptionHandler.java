package com.immoc.mall.exception;

import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.immoc.mall.enums.ResponseEnum.ERROR;
import static com.immoc.mall.enums.ResponseEnum.NEED_LOGIN;

@ControllerAdvice
public class RuntimExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle(RuntimeException e) {
        return ResponseVo.error(NEED_LOGIN);
    }
}
