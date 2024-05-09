package com.tnxts.easyuseclipboardbackend.exception;

import com.tnxts.easyuseclipboardbackend.utils.enums.ExceptionEnum;
import lombok.Data;

@Data
public class AuthorizeException extends RuntimeException{
    private String type;
    private String message;

    public AuthorizeException(ExceptionEnum resultEnum) {
        this.type = resultEnum.getType();
        this.message = resultEnum.getMessage();
    }
}
