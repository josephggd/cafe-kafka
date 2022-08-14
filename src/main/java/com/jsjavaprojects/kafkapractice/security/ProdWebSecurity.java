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
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
public class ProdWebSecurity {

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity httpSecurity ) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/api/orders/**", "/swagger-ui/**").permitAll();
        httpSecurity.formLogin().disable();
        httpSecurity.httpBasic().disable();
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("connect-src 'self' ; form-action 'self'; default-src 'self'");
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
