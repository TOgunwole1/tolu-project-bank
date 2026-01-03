package com.mudson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    private SupabaseJwtAuthFilter supabaseJwtAuthFilter;

    @Bean
    public FilterRegistrationBean<SupabaseJwtAuthFilter> jwtAuthFilterRegistration() {
        FilterRegistrationBean<SupabaseJwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(supabaseJwtAuthFilter);
        registration.addUrlPatterns("/*"); // Apply to all endpoints
        registration.setOrder(1);
        return registration;
    }
}
