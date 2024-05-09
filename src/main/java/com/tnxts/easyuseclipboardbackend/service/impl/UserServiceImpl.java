package com.tnxts.easyuseclipboardbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tnxts.easyuseclipboardbackend.dao.AuthorityDao;
import com.tnxts.easyuseclipboardbackend.dao.RoleAuthorityDao;
import com.tnxts.easyuseclipboardbackend.dao.UserDao;
import com.tnxts.easyuseclipboardbackend.domain.*;
import com.tnxts.easyuseclipboardbackend.exception.AuthorizeException;
import com.tnxts.easyuseclipboardbackend.service.UserService;
import com.tnxts.easyuseclipboardbackend.utils.enums.ExceptionEnum;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleAuthorityDao roleAuthorityDao;
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public String login() {
        return null;
    }

    @Override
    public ServerResponse register(RegisterInfo registerInfo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",registerInfo.getUsername());
        User user = userDao.selectOne(wrapper);
        if(user != null) {
//            return new ServerResponse(ResultEnum.CONFLICT, "用户已存在");
            throw new AuthorizeException(ExceptionEnum.USER_HAS_EXISTED);
        }
        else {
            // 创建用户
            user = new User();
            // username
            user.setUsername(registerInfo.getUsername());
            // password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash_pwd = encoder.encode(registerInfo.getPassword());
            user.setPassword(hash_pwd);
            // register_time
            LocalDateTime now = LocalDateTime.now();
            user.setRegisterTime(Timestamp.valueOf(now));
            // phone
            user.setPhone(registerInfo.getPhone());
            // nickname
            user.setNickname(registerInfo.getNickname());
            // 设定默认role
//            user.setRoleId(0);
            user.setRoleId((short)1);
            // 插入用户
            int count = userDao.insert(user);
            if(count > 0)
                return new ServerResponse(ResultEnum.OK);
            else
                return new ServerResponse(ResultEnum.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        if(user == null)
//        {
//            throw new RuntimeException("用户不存在");
//        }
        QueryWrapper<RoleAuthority> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",user.getRoleId());
        List<RoleAuthority> roleAuthorities = (ArrayList<RoleAuthority>) roleAuthorityDao.selectList(wrapper);
        List<Authority> authorities = new ArrayList<>();
        for(RoleAuthority roleAuthority : roleAuthorities)
        {
            QueryWrapper<Authority> authorityQueryWrapper = new QueryWrapper<>();
            authorityQueryWrapper.eq("authority_id",roleAuthority.getAuthorityId());
            authorities.add(authorityDao.selectOne(authorityQueryWrapper));
        }
        return authorities;
    }
}
