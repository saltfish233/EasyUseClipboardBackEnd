package com.tnxts.easyuseclipboardbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tnxts.easyuseclipboardbackend.dao.UserDao;
import com.tnxts.easyuseclipboardbackend.domain.CustomUserDetails;
import com.tnxts.easyuseclipboardbackend.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    private final UserService userService;

    public CustomUserDetailsService(UserDao userDao,UserService userService) {
        this.userDao = userDao;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<com.tnxts.easyuseclipboardbackend.domain.User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userDao.selectOne(wrapper);

        return new CustomUserDetails(user, this.userService.getAuthorities(user));
    }
}
