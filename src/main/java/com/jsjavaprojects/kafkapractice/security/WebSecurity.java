package com.jsjavaprojects.kafkapractice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/api/orders/**")
                .permitAll();
        httpSecurity.formLogin().disable();
        httpSecurity.httpBasic().disable();
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
