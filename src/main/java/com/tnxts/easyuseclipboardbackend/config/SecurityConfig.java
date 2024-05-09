package com.tnxts.easyuseclipboardbackend.config;

import com.tnxts.easyuseclipboardbackend.filter.ExceptionFilter;
import com.tnxts.easyuseclipboardbackend.filter.handler.UsernamePasswordAuthenticationFailureHandler;
import com.tnxts.easyuseclipboardbackend.filter.handler.UsernamePasswordAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tnxts.easyuseclipboardbackend.filter.TokenFilter;

@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authManager(http);
        UsernamePasswordAuthenticationFilter passwordAuthenticationFilter = usernamePasswordAuthenticationFilter(authenticationManager);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(passwordAuthenticationFilter)
                .addFilterBefore(tokenFilter(authenticationManager), passwordAuthenticationFilter.getClass())
                .addFilterBefore(exceptionFilter(),TokenFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/user/register").permitAll()  // 允许所有人访问 /register
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()); // 定义登录页并允许所有人访问登录页
//                .logout(LogoutConfigurer::permitAll); // 允许所有人访问登出页

        return http.build();
    }
    @Bean
    public UsernamePasswordAuthenticationSuccessHandler usernamePasswordAuthenticationSuccessHandler() {
        return new UsernamePasswordAuthenticationSuccessHandler();
    }

    @Bean
    public UsernamePasswordAuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler() {
        return new UsernamePasswordAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public TokenFilter tokenFilter(AuthenticationManager authenticationManager) {
        return new TokenFilter(authenticationManager);
    }

    @Bean
    public ExceptionFilter exceptionFilter() {
        return new ExceptionFilter();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(usernamePasswordAuthenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(usernamePasswordAuthenticationFailureHandler());

        return authenticationFilter;
    }

}
