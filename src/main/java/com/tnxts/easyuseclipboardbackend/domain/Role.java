package com.tnxts.easyuseclipboardbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Role {
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;
    private String roleName;
}
