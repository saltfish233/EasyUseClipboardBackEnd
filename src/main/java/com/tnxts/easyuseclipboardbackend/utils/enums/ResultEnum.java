package com.tnxts.easyuseclipboardbackend.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    OK(200,"成功"),
    BAD_REQUEST(400,"请求错误"),
    UNAUTHORIZED(401,"未登录"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"资源未找到"),
    CONFLICT(409,"资源冲突"),
    TOO_MANY_REQUESTS(429,"请求过多"),
    GATEWAY_TIMEOUT(502,"网关无响应"),
    SERVICE_UNAVAILABLE(503,"服务器异常");

    private final Integer code;
    private String msg;

    public ResultEnum customMessage(String msg)
    {
        this.msg = msg;
        return this;
    }
}