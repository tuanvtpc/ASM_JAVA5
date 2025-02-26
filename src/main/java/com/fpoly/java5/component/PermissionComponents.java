package com.fpoly.java5.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionComponents implements HandlerInterceptor {
	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// /users
		// Nếu tài khoản chưa đăng nhập thì không vào được những trang ngoài danh sách
		// loại bỏ
		// Nếu chưa đăng nhập => chuyển về trang /login

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			response.sendRedirect("/login");
			return false;
		}

		// role = 0 => admin => /users
		// role = 1 => user => /add-student

		String path = request.getServletPath(); // ?
		int role = -1; // ?

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				role = Integer.parseInt(cookie.getValue());
				break;
			}
		}

		if (role == 0 && path.contains("/admin")) {
			return true;
		}

		if (role == 1 && path.contains("/user")) {
			return true;
		}

		response.sendRedirect("/login");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
