package com.veyit.waterstoragesystem.config;


import com.veyit.waterstoragesystem.interceptor.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置
        registry.addMapping("/**").allowedOrigins("*");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/**")
                .addPathPatterns("/waterStorage/**")
                .excludePathPatterns("/user/forget/**");
    }
}
