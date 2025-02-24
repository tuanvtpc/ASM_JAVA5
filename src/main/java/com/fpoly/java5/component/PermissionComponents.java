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

		String paths = request.getServletPath();
		// Bỏ qua các tài nguyên tĩnh (CSS, JS, hình ảnh)
		if (paths.startsWith("/css/") || paths.startsWith("/js/") || paths.startsWith("/img/")) {
			return true; // Cho phép truy cập tài nguyên tĩnh mà không cần kiểm tra đăng nhập
		}

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
