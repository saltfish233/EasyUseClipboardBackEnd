package com.tnxts.easyuseclipboardbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RoleAuthority {
    @TableId(value = "role_authority_id", type = IdType.AUTO)
    private Long roleAuthorityId;
    private Long roleId;
    private Long authorityId;
}
