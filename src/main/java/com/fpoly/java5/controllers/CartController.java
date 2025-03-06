package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CartDetailJPA;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ProductJPA;
import com.fpoly.java5.services.CartService;

@Controller
public class CartController {
	
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	CartDetailJPA cartDetailJPA;
	
	@Autowired
	CartService cartService;
	
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategory() {
		return categoryJPA.findAll();
	}
	
	@ModelAttribute("totalCartItem")
	public int getTotalCartItem() {
	    CartEntity cartEntity = cartService.getCart();
	    if (cartEntity != null) {
	        return cartDetailJPA.sumQuantityByCartId(cartEntity.getId());
	    }
	    return 0; 
	}

	@GetMapping("/cart")
	public String cartLayout(Model model) {
		model.addAttribute("items", cartService.getList()); 
		model.addAttribute("totalPrice", cartService.getTotalPrice());
		return "/user/cart.html";
	}

	@PostMapping("/add-to-cart")
	public String addToCart(
	    @RequestParam int prodId,
	    @RequestParam(required = false) String detailProd) { 
	    cartService.addToCart(prodId);

	    if (detailProd == null || detailProd.isEmpty()) {
	        return "redirect:/homepage";
	    } else {
	        return "redirect:/detail-product?prodId=" + prodId;
	    }
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
