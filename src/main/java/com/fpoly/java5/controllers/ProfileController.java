package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fpoly.java5.entity.WishlistEntity;
import com.fpoly.java5.jpas.WishlistJPA;

@Controller
public class ProfileController {
	@Autowired
    private WishlistJPA wishlistJPA;
	
	 @GetMapping("/profile")
	    public String profileUser(Model model) {
	        Integer userId = 1; // Thay bằng user đăng nhập
	        List<WishlistEntity> wishlist = wishlistJPA.findByUserId(userId);
	        model.addAttribute("wishlist", wishlist);
	        return "/user/profile.html";
	    }
	 @GetMapping("/wishlist")
	 public String wishlistPage(Model model) {
	     Integer userId = 1; // Giả lập user đăng nhập (sau này thay bằng session)
	     List<WishlistEntity> wishlist = wishlistJPA.findByUserId(userId);
	     model.addAttribute("wishlist", wishlist);
	     return "/user/wishlist";
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
