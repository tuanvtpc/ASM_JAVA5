package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckOutController {
	
	@GetMapping("/checkout")
	public String checkoutLayout() {
		return "/user/checkout.html";
	}
	
}
