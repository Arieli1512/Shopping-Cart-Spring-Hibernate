package com.jakubw.zaip.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
@EnableWebSecurity

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("../resources/**")).permitAll()
                        .requestMatchers("/login","/register","/products/**","/order/**","/css/**").permitAll()
                        .requestMatchers("/accountInfo").authenticated()
                        .anyRequest().hasRole("ADMIN")
                )
                .headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("../resources/**"))
                ).requestCache().disable()
                .formLogin()
                .loginPage("/login") // Specify custom login page URL
                .defaultSuccessUrl("/products") // Specify default success URL after successful login
                .and()
                .logout()
                .logoutUrl("/logout") // Specify logout URL
                .logoutSuccessUrl("/login?logout") // Specify redirect URL after logout
                .and()
                .csrf().disable();

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
