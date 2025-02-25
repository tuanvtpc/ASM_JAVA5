package com.fpoly.java5.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.beans.UserBean;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.CartService;
import com.fpoly.java5.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Controller
public class ProfileController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;
	@Autowired
	UserJPA userJPA;

	@GetMapping("/profile")
	public String profileUser(Model model) {
		UserEntity user = cartService.getUser();
		if (user != null) {
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "User not found");
			return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
		}
		return "/user/profile.html";
	}

	@GetMapping("/update-profile")
	public String editProfile(Model model) {
	    UserEntity user = cartService.getUser();
	    UserEntity userEntity = userService.getUserById(user.getId()); 

	    model.addAttribute("user", userEntity);
	    return "/user/edit-profile.html";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@RequestParam("name") String name,
	                            @RequestParam("email") String email,
	                            @RequestParam("phone") String phone,
	                            Model model) {
	    UserEntity user = cartService.getUser();
	    if (user == null) {
	        return "redirect:/login";
	    }

	    boolean hasError = false;

	    // Kiểm tra lỗi
	    if (name.trim().isEmpty()) {
	        model.addAttribute("nameError", "Họ và Tên không được để trống");
	        hasError = true;
	    }
	    if (email.trim().isEmpty()) {
	        model.addAttribute("emailError", "Email không được để trống");
	        hasError = true;
	    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
	        model.addAttribute("emailError", "Email không hợp lệ");
	        hasError = true;
	    }
	    if (phone.trim().isEmpty()) {
	        model.addAttribute("phoneError", "Số điện thoại không được để trống");
	        hasError = true;
	    } else if (!phone.matches("\\d{10,11}")) {
	        model.addAttribute("phoneError", "Số điện thoại phải có 10-11 chữ số");
	        hasError = true;
	    }

	    if (hasError) {
	        model.addAttribute("user", user);
	        return "/user/edit-profile.html";
	    }

	    // Cập nhật thông tin user
	    user.setName(name);
	    user.setEmail(email);
	    user.setPhone(phone);
	    userJPA.save(user);

	    return "redirect:/profile";
	}

	@GetMapping("/change-password")
	public String changePassword() {
		return "/user/change-password.html";
	}
	
}
