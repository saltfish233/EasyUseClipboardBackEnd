package com.tnxts.easyuseclipboardbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tnxts.easyuseclipboardbackend.domain.RegisterInfo;
import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;


public interface UserService extends IService<User> {
    public String login();
    public ServerResponse register(RegisterInfo registerInfo);
    public Collection<? extends GrantedAuthority> getAuthorities(User user);
}