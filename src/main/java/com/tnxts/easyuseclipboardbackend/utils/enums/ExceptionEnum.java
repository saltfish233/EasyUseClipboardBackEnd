package com.tnxts.easyuseclipboardbackend.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    NO_SUCH_COMMODITY("资源异常","该商品不存在"),
    JWT_INVALID("认证异常","token无效"),
    JWT_EXPIRED("认证异常","token已过期"),
    WECHAT_CODE_INVALID("认证异常","code无效"),
    USER_HAS_EXISTED("认证异常","用户已存在")
    ;

    private final String type;
    private final String message;
}
