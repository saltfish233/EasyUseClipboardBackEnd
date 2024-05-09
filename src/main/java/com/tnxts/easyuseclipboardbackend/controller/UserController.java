package com.tnxts.easyuseclipboardbackend.controller;

import com.tnxts.easyuseclipboardbackend.domain.RegisterInfo;
import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.service.UserService;
import com.tnxts.easyuseclipboardbackend.service.impl.UserServiceImpl;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("register")
    public ServerResponse register(@RequestBody RegisterInfo registerInfo){
        return userService.register(registerInfo);
    }

    @GetMapping("keepalive")
    @PreAuthorize("hasAuthority('KEEP_ALIVE')")
    public ServerResponse keepalive(){
        return new ServerResponse(ResultEnum.OK);
    }
}
