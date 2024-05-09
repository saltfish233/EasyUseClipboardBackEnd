package com.tnxts.easyuseclipboardbackend.controller;

import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vlogin")
public class LoginController {
    @GetMapping("autologin")
    @PreAuthorize("hasAuthority('LOGIN_AUTO')")
    public ServerResponse autoLogin()
    {
        return new ServerResponse(ResultEnum.OK);
    }
}
