package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InterceptorConfig implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("pre handle");
//		Nơi để kiểm tra vai trò và user đã đăng nhập chưa
//		Lấy thông tin userId và role từ cookie
//		Kiểm tra thông tin từ cookie và vai trò khớp với từng url: /user/**, /admin/**
		String userId = null;
		String role = null;
		Cookie cookies[] = request.getCookies();
		if (cookies == null) {
			response.sendRedirect("/login");
			return false;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
			if (cookie.getName().equals("role")) {
				role = cookie.getValue();
			}
		}
		if (userId == null || role == null) {
			response.sendRedirect("/login");
			return false;
		}
		String path = request.getRequestURI();
		if (path.startsWith("/admin") && !role.equals("0")) {
			response.sendRedirect("/login");
			return false;
		}
		if (path.startsWith("/user") && !role.equals("1")) {
			response.sendRedirect("/login");
			return false;
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("post handle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("after completion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
