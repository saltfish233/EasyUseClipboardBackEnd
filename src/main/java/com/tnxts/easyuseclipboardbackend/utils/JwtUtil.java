package com.tnxts.easyuseclipboardbackend.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//注册组件
@Component
@Data
@Slf4j
public class JwtUtil {
    //创建对象主体
    private final String CLAIM_KEY_USERNAME = "uuid";
    //创建创建时间
    private final String CLAIM_KEY_CREATED = "created";


    //@Value这个注解一定要引入spring-boot-starter-validation才能使用
    //@Value注解可以代替@Data和@ConfigurationProperties结合
    //这两个二者选一即可
    //我建议使用@Data和@ConfigurationProperties结合
    @Value("${jwt.data.SECRET}")
    private String SECRET;//创建加密盐

    //过期时间
    @Value("${jwt.data.expiration}")
    private Long expiration;

    //根据用户名生成token
    //传入的是使用SpringSecurity里的UserDetails
    public String createToken(String uuid) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, uuid);
//        claims.put(CLAIM_KEY_CREATED, new Date());
        try
        {
            return createToken(claims);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
    }

    //根据token获取uuid
    public String getUUIDFromToken(String token) {
        String uuid = "";
        try {
            Claims claims = getClaimsFromToken(token);
            uuid = (String) claims.get("uuid");
        } catch (Exception e) {
            uuid = null;
            log.info("error:{}", "uuid未能获取 from token");
            throw e;
        }
        return uuid;
    }

    //根据token获取用户名
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (String) claims.get("username");

    }

    //从token中获取荷载
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | SignatureException | IllegalArgumentException | MalformedJwtException |
                 UnsupportedJwtException e) {
//            throw new JwtException(ResultEnum.JWT_INVALID);
            throw e;
        }
        return claims;
    }


    //根据负载生成jwt token
    private String createToken(Map<String, Object> claims) {
        //jjwt构建jwt builder
        //设置信息，过期时间，signnature
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //生成token失效时间
    private Date expirationDate() {
        //失效时间为：系统当前毫秒数+我们设置的时间（s）*1000=》毫秒
        //其实就是未来7天
        System.out.println(new Date(System.currentTimeMillis() + expiration * 1000));
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    //判断token是否有效
    public boolean validateToken(String token, UserDetails userDetails) {
        //判断token是否过期
        //判断token是否和userDetails中的一致
        //我们要做的 是先获取用户名
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //判断token、是否失效
    //失效返回true
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFeomToken(token);
        return expiredDate.before(new Date());
    }

    //从荷载中获取时间
    private Date getExpiredDateFeomToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    //判断token是否可以被刷新
    //过期（销毁）就可以
    public boolean canBeRefreshed(String token){
        return !isTokenExpired(token);
    }

    //刷新token
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        //修改为当前时间
        claims.put(CLAIM_KEY_CREATED,new Date());
        return createToken(claims);
    }
}
