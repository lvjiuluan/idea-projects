package com.immoc.mall.exception;

import com.immoc.mall.enums.ResponseEnum;
import com.immoc.mall.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.immoc.mall.enums.ResponseEnum.ERROR;

@ControllerAdvice
public class RuntimExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ERROR, e.getMessage());
    }
}
