package com.jsjavaprojects.kafkapractice.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "security.enabled", havingValue = "false")
public class DevWebSecurity {

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/**").permitAll();
        httpSecurity.formLogin().disable();
        httpSecurity.httpBasic().disable();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
