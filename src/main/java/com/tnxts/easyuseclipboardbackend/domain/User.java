package com.tnxts.easyuseclipboardbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tnxts.easyuseclipboardbackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Data
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    private Short roleId;        // Type changed to Byte to reflect the TINYINT SQL type
    private String nickname;    // New field to store the user's nickname
    private String username;    // New field to store the user's login name
    private String password;
    private String phone;
    private Timestamp registerTime;
    private Timestamp loginTime;
    private Boolean online;        // Type changed to Byte for the TINYINT SQL type
}
