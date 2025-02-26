package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.entity.WishlistEntity;
import com.fpoly.java5.jpas.WishlistJPA;
import com.fpoly.java5.services.AddressService;

@Controller
public class ProfileController {
	@Autowired
    private WishlistJPA wishlistJPA;
	
    @Autowired
    private AddressService addressService;
    
	 @GetMapping("/profile")
	 public String getAddressList(Model model) {
	        Integer userId = 1; // Thay bằng User đăng nhập
	        UserEntity user = new UserEntity(); 
	        user.setId(userId); 
	        List<AddressEntity> addresses = addressService.getAddressesByUser(user);
	        model.addAttribute("addresses", addresses);
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
	@GetMapping("/edit-address")
	public String editAddress() {
		return "/user/edit-address.html";
	}
}
