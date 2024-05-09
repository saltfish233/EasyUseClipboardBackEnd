package com.tnxts.easyuseclipboardbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RegisterInfo {
    private String username;
    private String password;
    private String nickname;
    private String phone;
}
