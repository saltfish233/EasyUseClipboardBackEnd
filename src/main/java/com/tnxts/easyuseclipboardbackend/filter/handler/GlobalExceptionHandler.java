package com.tnxts.easyuseclipboardbackend.filter.handler;

import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.exception.AuthorizeException;
import com.tnxts.easyuseclipboardbackend.utils.enums.ExceptionEnum;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AuthorizeException.class)
    @ResponseBody
    public ServerResponse handleAuthorizeException(AuthorizeException e){
        log.error(e.getType() + "！原因是：" + e.getMessage());
        e.printStackTrace();
        return new ServerResponse(ResultEnum.CONFLICT.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse handleException(Exception e){
        log.error("未知异常！原因是："+e.getMessage());
        e.printStackTrace();
        return new ServerResponse(ResultEnum.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ServerResponse handleRuntimeException(RuntimeException e){
        log.error("未知异常！原因是："+e.getMessage());
        e.printStackTrace();
        return new ServerResponse(ResultEnum.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    @ResponseBody
    public ServerResponse handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e){
        log.error("内部认证服务异常！原因是："+e.getMessage());
        e.printStackTrace();
        return new ServerResponse(ResultEnum.SERVICE_UNAVAILABLE);
    }

}
