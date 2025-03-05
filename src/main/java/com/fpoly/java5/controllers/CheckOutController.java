package com.fpoly.java5.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.AddressJPA;
import com.fpoly.java5.jpas.OrderDetailJPA;
import com.fpoly.java5.jpas.OrderJPA;
import com.fpoly.java5.services.CartService;
import com.fpoly.java5.services.OrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckOutController {

	@Autowired
	AddressJPA addressJPA;

	@Autowired
	CartService cartService;

	@Autowired
	OrderJPA orderJPA;

	@Autowired
	OrderDetailJPA orderDetailJPA;

	@Autowired
	OrderService orderService;

	@GetMapping("/user/checkout")
	public String checkoutLayout(Model model) {
		model.addAttribute("items", cartService.getList());
		model.addAttribute("totalPrice", cartService.getTotalPrice());
		return "/user/checkout.html";
	}

	@PostMapping("/user/checkout")
	public String checkout(
<<<<<<< HEAD
			@RequestParam("paymentMethod") int paymentMethod, 
			@RequestParam("selectedAddressId") int selectedAddressId,
			Model model) {
=======
			@RequestParam("paymentMethod") int paymentMethod, Model model) {
>>>>>>> parent of b094af6 (profile-user)
		try {
			
			
			if (paymentMethod < 0 || paymentMethod > 2) {
	            model.addAttribute("error", "Phương thức thanh toán không hợp lệ.");
	            return "/user/checkout.html";
	        }

			orderService.CreateOrder(2, paymentMethod);
			return "redirect:/cart";
		} catch (Exception e) {
			model.addAttribute("error", "Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại.");
			return "/user/checkout.html";
		}
	}

	@ModelAttribute("addressList")
	public List<AddressEntity> getListAddress() {
		UserEntity user = cartService.getUser();
		if (user == null) {
			System.out.println("User not found");
			return new ArrayList<>();
		}
		List<AddressEntity> addresses = addressJPA.findByUserId(user.getId());
		System.out.println("Addresses found: " + addresses.size());
		return addresses;
	}

}
