package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProfileController {

	@GetMapping("/profile")
	public String profileUser(Model model) {
		return "/user/profile.html";
	}

	@GetMapping("/edit-profile")
	public String getMethodName() {
		return "/user/edit-profile.html";
	}

	@GetMapping("/change-password")
	public String changePassword() {
		return "/user/change-password.html";
	}
}
