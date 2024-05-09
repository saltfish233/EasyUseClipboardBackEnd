package com.tnxts.easyuseclipboardbackend.domain;

import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse {
    private Integer code;
    private String msg;
    private Object data;

    public ServerResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(ResultEnum R){
        this.code = R.getCode();
        this.msg = R.getMsg();
    }

    public ServerResponse(ResultEnum R,Object data){
        this.code = R.getCode();
        this.msg = R.getMsg();
        this.data = data;
    }

    public ServerResponse(Object data) {
        this.code = ResultEnum.OK.getCode();
        this.msg = ResultEnum.OK.getMsg();
        this.data = data;
    }
}