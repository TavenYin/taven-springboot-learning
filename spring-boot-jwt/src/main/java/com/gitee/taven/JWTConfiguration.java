package com.gitee.taven;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JWTConfiguration implements WebMvcConfigurer  {
	
	@Bean
	public JwtTokenInterceptor jwtTokenInterceptor() {
		return new JwtTokenInterceptor();
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor()).addPathPatterns("/**");
    }
    
}
