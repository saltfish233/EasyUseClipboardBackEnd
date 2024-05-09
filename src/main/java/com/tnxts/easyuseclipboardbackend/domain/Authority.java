package com.tnxts.easyuseclipboardbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {
    @TableId(value = "authority_id", type = IdType.AUTO)
    private Long authorityId;
    private String authorityName;

    @Override
    public String getAuthority() {
        return this.authorityName;
    }
}
