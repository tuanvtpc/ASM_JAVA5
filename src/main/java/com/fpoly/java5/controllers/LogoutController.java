package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
    @GetMapping("/logoutt")
    public String logout(HttpServletResponse response) {
        Cookie cookieUserId = new Cookie("user_id", null);
        cookieUserId.setMaxAge(0);
        cookieUserId.setPath("/");
        response.addCookie(cookieUserId);

        Cookie cookieRole = new Cookie("role", null);
        cookieRole.setMaxAge(0);
        cookieRole.setPath("/");
        response.addCookie(cookieRole);

        return "redirect:/login"; 
    }
}