package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartController {
	
	@GetMapping("/shopping-cart")
	public String shoppingCart() {
		return "/user/order.html";
	}
	
}
