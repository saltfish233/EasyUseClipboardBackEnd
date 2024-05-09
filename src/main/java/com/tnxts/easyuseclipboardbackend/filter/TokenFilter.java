package com.tnxts.easyuseclipboardbackend.filter;

import com.tnxts.easyuseclipboardbackend.domain.CustomUserDetails;
import com.tnxts.easyuseclipboardbackend.domain.ServerResponse;
import com.tnxts.easyuseclipboardbackend.domain.TokenInfo;
import com.tnxts.easyuseclipboardbackend.service.CustomUserDetailsService;
import com.tnxts.easyuseclipboardbackend.utils.JwtUtil;
import com.tnxts.easyuseclipboardbackend.utils.RedisCache;
import com.tnxts.easyuseclipboardbackend.utils.enums.ExceptionEnum;
import com.tnxts.easyuseclipboardbackend.utils.enums.ResultEnum;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Value("${jwt.data.expiration}")
    private Integer expiration;

    private final AuthenticationManager authenticationManager;

    public TokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Object attribute = request.getAttribute("filter.exception");
        if(attribute != null ){
            filterChain.doFilter(request,response);
            return ;
        }

        String token = request.getHeader("Authorization");
        String uuid = "";

        System.out.println("TokenFilter");
        if (token != null && !token.isEmpty()) {
            try {

                uuid = jwtUtil.getUUIDFromToken(token);
                if(redisCache.getCacheObject(uuid) == null) {
                    throw new RuntimeException("token异常");
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext(); // clear context if authentication fails
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + e.getMessage());
                return; // Exit filter chain if authentication fails
            }
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(!Objects.equals(uuid, "") && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            TokenInfo info  = new TokenInfo(redisCache.getCacheObject(uuid));
            CustomUserDetails principle = (CustomUserDetails) customUserDetailsService.loadUserByUsername(info.getUsername());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principle,null,principle.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            redisCache.expire(uuid,expiration, TimeUnit.MINUTES);
        }
        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
