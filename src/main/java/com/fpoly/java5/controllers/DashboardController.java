package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping("/admin/dashboard")
	public String dashBoard() {
		return "/admin/dashboard.html";
	}
	
}
