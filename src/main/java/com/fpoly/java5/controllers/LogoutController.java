package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
    @GetMapping("/logoutt")
    public String logout(HttpServletResponse response) {
        // Xóa cookie user_id
        Cookie cookieUserId = new Cookie("user_id", null);
        cookieUserId.setMaxAge(0); // Hết hạn ngay lập tức
        cookieUserId.setPath("/");
        response.addCookie(cookieUserId);

        // Xóa cookie role (nếu có)
        Cookie cookieRole = new Cookie("role", null);
        cookieRole.setMaxAge(0);
        cookieRole.setPath("/");
        response.addCookie(cookieRole);

        return "redirect:/login"; // Chuyển về trang đăng nhập
    }
}
