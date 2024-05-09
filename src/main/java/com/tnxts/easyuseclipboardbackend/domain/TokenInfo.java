package com.tnxts.easyuseclipboardbackend.domain;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String username;
    //    private short roleId;
    private Collection<? extends GrantedAuthority> authorities;
//    private long latestLoginTime;

    @SuppressWarnings("unchecked")
    public TokenInfo(JSONObject object)
    {
        this.username = object.get("username").toString();
        List<Authority> authorities = new ArrayList<>();
        for(JSONObject authority : (List<JSONObject>)object.get("authorities"))
        {
            Authority auth = new Authority();
            auth.setAuthorityId(((Integer)authority.get("authorityId")).longValue());
            auth.setAuthorityName((String) authority.get("authorityName"));
            authorities.add(auth);
        }
        this.authorities = authorities;
    }
}
