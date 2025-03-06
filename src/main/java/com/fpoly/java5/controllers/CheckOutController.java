package com.fpoly.java5.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.AddressJPA;
import com.fpoly.java5.jpas.CartDetailJPA;
import com.fpoly.java5.jpas.CategoryJPA;
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

	@Autowired
	CategoryJPA categoryJPA;

	@Autowired
	CartDetailJPA cartDetailJPA;

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

	@GetMapping("/user/checkout")
	public String checkoutLayout(Model model) {
		model.addAttribute("items", cartService.getList());
		model.addAttribute("totalPrice", cartService.getTotalPrice());
		return "/user/checkout.html";
	}

	@PostMapping("/user/checkout")
	public String checkout(@RequestParam(value = "paymentMethod", required = false) Integer paymentMethod,
			@RequestParam(value = "selectedAddressId", required = false) Integer selectedAddressId, Model model) {
		try {
			model.addAttribute("items", cartService.getList());
			if (selectedAddressId == null || selectedAddressId <= 0) {
				model.addAttribute("error", "Vui lòng chọn địa chỉ nhận hàng.");
				return "/user/checkout.html";
			}

			if (paymentMethod == null || paymentMethod < 0 || paymentMethod > 2) {
				model.addAttribute("error", "Phương thức thanh toán không hợp lệ.");
				return "/user/checkout.html";
			}

			orderService.CreateOrder(selectedAddressId, paymentMethod);
			return "redirect:/cart";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại. Chi tiết: " + e.getMessage());
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
