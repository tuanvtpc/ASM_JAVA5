package com.fpoly.java5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("error", "");
		return "/user/login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {

		if (username == null || username.trim().isEmpty()) {
			model.addAttribute("errorUsername", "Tên đăng nhập không được để trống");
			return "/user/login.html";
		}

		if (password == null || password.trim().isEmpty()) {
			model.addAttribute("errorPassword", "Mật khẩu không được để trống");
			return "/user/login.html";
		}

		try {
			UserEntity user = userService.authenticateAndSetCookies(username, password);

			if (user.getRole() == 0) {
				return "redirect:/admin/dashboard";
			} else if (user.getRole() == 1) {
				return "redirect:/homepage";
			} else {
				model.addAttribute("error", "Thông tin đăng nhập không hợp lệ");
				return "/user/login.html";
			}
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "/user/login.html";
		}
	}

}
