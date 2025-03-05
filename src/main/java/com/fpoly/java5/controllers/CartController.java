package com.fpoly.java5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.services.CartService;

@Controller
public class CartController {

	@Autowired
	CartService cartService;

	@GetMapping("/cart")
	public String cartLayout(Model model) {
		model.addAttribute("items", cartService.getList()); 
		model.addAttribute("totalPrice", cartService.getTotalPrice());
		return "/user/cart.html";
	}

	@PostMapping("/add-to-cart")
	public String addToCart(@RequestParam int prodId) {
		cartService.addToCart(prodId);
		

		return "redirect:/homepage";
	}

	@PostMapping("/delete-cart-item")
	public String deleteCartItem(@RequestParam int cartItemId) {
		cartService.deleteCartItem(cartItemId);
		return "redirect:/cart";
	}
	
	@PostMapping("/update-cart-item")
	public String updateCartItem(@RequestParam int cartItemId, @RequestParam int quantity) {
	    cartService.updateQuantityCartItem(cartItemId, quantity);
	    return "redirect:/cart";
	}

 
	
}
