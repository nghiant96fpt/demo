package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

	@Autowired
	InterceptorConfig interceptorConfig;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorConfig).addPathPatterns("/admin/**", "/user/**");
	}
}

// tạo url định hình trước url này cần phải đăng nhập không?
// nếu cần thì nó là vai trò gi?

// Những chức năng không cần đăng nhập: /**
// /, /product-detail, /login, /register, /forgot-password,....

// Những trang cần đăng nhập nhưng với vai trò user: /user/**
// /user/cart, /user/orders, /user/order-detail, /user/change-password

//Những trang cần đăng nhập nhưng với vai trò admin: /admin/**