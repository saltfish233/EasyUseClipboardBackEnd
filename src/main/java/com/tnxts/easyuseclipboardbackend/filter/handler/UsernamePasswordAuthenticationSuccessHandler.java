package com.tnxts.easyuseclipboardbackend.filter.handler;

import com.alibaba.fastjson2.JSON;
import com.tnxts.easyuseclipboardbackend.domain.CustomUserDetails;
import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.domain.TokenInfo;
import com.tnxts.easyuseclipboardbackend.domain.User;
import com.tnxts.easyuseclipboardbackend.utils.JwtUtil;
import com.tnxts.easyuseclipboardbackend.utils.RedisCache;
import com.tnxts.easyuseclipboardbackend.utils.enums.CommonEnum;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisCache redisCache;

    @Value("${jwt.data.expiration}")
    private Integer expiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");// 加上这个处理问号
        // 生成token
        System.out.println("UsernameAuthenticationSuccess");
        String token;
        String uuid = UUID.randomUUID().toString().replace("-","");
        try
        {
            token = jwtUtil.createToken(uuid);
        }
        catch (RuntimeException ex) {
            throw ex;
        }

        System.out.println(authentication);
//        Map<String,Object> res = new HashMap<>();
        // token信息存入redis, 缓存时间单位为秒
        ServerResponse res = new ServerResponse();

        if(authentication != null)
        {
            res.setCode(ResultEnum.OK.getCode());
            res.setMsg(ResultEnum.OK.getMsg());

            User user = ((CustomUserDetails)authentication.getPrincipal()).getUser();
            TokenInfo info = new TokenInfo();
            info.setUsername(user.getUsername());
            info.setAuthorities(authentication.getAuthorities());
            redisCache.setCacheObject(uuid,info,expiration, TimeUnit.MINUTES);

            Map<String,Object> result = new HashMap<>();
            result.put("Authorization", token);
            result.put("user", user);

            res.setData(result);
        }


        // token添加到返回体里
//        res.put(CommonEnum.AUTHORIZATION.getKey(), token);


        response.getWriter().write(JSON.toJSONString(res));
    }
}
