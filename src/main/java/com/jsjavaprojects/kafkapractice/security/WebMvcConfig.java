package com.jsjavaprojects.kafkapractice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    protected static final String[] allowedVerbs = new String[] {
            "GET", "PUT", "POST"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
            .addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
            .allowedMethods(allowedVerbs)
            .allowCredentials(true);
    }

    @Bean
    public StrictHttpFirewall setFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList(allowedVerbs));
        return firewall;
    }
}
