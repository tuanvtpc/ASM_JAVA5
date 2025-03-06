package com.fpoly.java5.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.java5.entity.CartEntity;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.OrderDetailEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.CartDetailJPA;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.OrderDetailJPA;
import com.fpoly.java5.jpas.OrderJPA;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.CartService;
import com.fpoly.java5.services.ImageService;
import com.fpoly.java5.services.UserService;

@Controller
public class ProfileController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;
	@Autowired
	UserJPA userJPA;

	@Autowired
	OrderJPA orderJPA;

	@Autowired
	OrderDetailJPA orderDetailJPA;

	@Autowired
	ImageService imageService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
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

	@GetMapping("/user/profile")
	public String profileUser(Model model) {
		UserEntity user = cartService.getUser();
		if (user != null) {
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "User not found");
			return "redirect:/login";
		}
		return "/user/profile.html";
	}

	@GetMapping("/user/update-profile")
	public String editProfile(Model model) {
		UserEntity user = cartService.getUser();
		UserEntity userEntity = userService.getUserById(user.getId());

		model.addAttribute("user", userEntity);
		return "/user/edit-profile.html";
	}

	@PostMapping("/user/update-profile")
	public String updateProfile(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("phone") String phone, Model model) {
		UserEntity user = cartService.getUser();
		if (user == null) {
			return "redirect:/login";
		}

		boolean hasError = false;

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

		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		userJPA.save(user);

		return "redirect:/profile";
	}

	public List<OrderEntity> getListOrder() {
		return orderJPA.getListOrderByUser(null);
	}

	@GetMapping("/user/order")
	public String orderLayout(@RequestParam(name = "status", required = false) Integer status, Model model) {
		UserEntity user = cartService.getUser();

		List<OrderEntity> orders;
		if (status != null) {
			orders = orderJPA.findByUserIdAndStatus(user.getId(), status);
		} else {
			orders = orderJPA.getListOrderByUser(user.getId());
		}

		model.addAttribute("order", orders != null ? orders : new ArrayList<>());
		return "user/order.html";
	}

	@PostMapping("/user/update-statusOrder")
	public String updateOrderStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status,
			RedirectAttributes redirectAttributes) {
		Optional<OrderEntity> orderOptional = orderJPA.findById(id);
		if (orderOptional.isPresent()) {
			OrderEntity order = orderOptional.get();
			int currentStatus = order.getStatus();

			if (isValidStatus(currentStatus, status)) {
				order.setStatus(status);
				orderJPA.save(order);
				redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái đơn hàng thành công.");
			} else {
				redirectAttributes.addFlashAttribute("error", "Không thể cập nhật trạng thái đơn hàng từ "
						+ getStatusName(currentStatus) + " sang " + getStatusName(status));
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng với ID: " + id);
		}
		return "redirect:/user/order";
	}

	private boolean isValidStatus(int currentStatus, int newStatus) {
		switch (currentStatus) {
		case 0: // Chờ xác nhận
			return newStatus == 6;
		case 1: // Đã xác nhận
			return false;
		case 2: // Đang giao
			return false; // Chỉ có thể chuyển sang đã giao
		case 3: // Đã giao
			return newStatus == 4 || newStatus == 5; // Chỉ có thể chuyển sang đã nhận hoặc trả hàng
		case 4: // Đã nhận
			return newStatus == 5; // Có thể chuyển sang trả hàng
		case 5: // Trả hàng
			return false;
		case 6: // Hủy đơn
			return false; // Không thể thay đổi trạng thái
		default:
			return false;
		}
	}

	@GetMapping("/user/detail-order")
	public String orderDetailLayout(@RequestParam("id") Integer id, Model model) {

		Optional<OrderEntity> orderOptional = orderJPA.findById(id);

		if (orderOptional.isPresent()) {
			OrderEntity order = orderOptional.get();
			model.addAttribute("order", order);

			List<OrderDetailEntity> orderDetail = orderDetailJPA.findByIdOrder(id);
			model.addAttribute("orderDetailList", orderDetail);
		} else {
			model.addAttribute("error", "Không tìm thấy đơn hàng với ID: " + id);
		}
		return "user/detail-order.html";
	}

	private String getStatusName(int status) {
		switch (status) {
		case 0:
			return "Chờ xác nhận";
		case 1:
			return "Đã xác nhận";
		case 2:
			return "Đang giao";
		case 3:
			return "Đã giao hàng";
		case 4:
			return "Hoàn tất";
		case 5:
			return "Trả hàng";
		case 6:
			return "Hủy đơn";
		default:
			return "Không xác định";
		}
	}

	@PostMapping("/user/update-avatar")
	public String updateAvatar(@RequestParam("avatar") MultipartFile avatar, Model model) {
		UserEntity user = cartService.getUser();
		if (user == null) {
			return "redirect:/login";
		}

		if (avatar.isEmpty()) {
			model.addAttribute("avatarError", "Vui lòng chọn ảnh");
			return "/user/edit-profile.html";
		}

		// Lưu ảnh vào thư mục và lấy tên file
		String fileName = imageService.saveImage(avatar);
		if (fileName == null) {
			model.addAttribute("avatarError", "Lỗi khi lưu ảnh");
			return "/user/edit-profile.html";
		}

		// Cập nhật avatar cho user
		user.setAvatar(fileName);
		userJPA.save(user);

		return "redirect:/user/profile";
	}

	@GetMapping("/user/change-password")
	public String changePassword(Model model) {
		UserEntity user = cartService.getUser();
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		return "user/change-password.html";
	}

	@PostMapping("/user/change-password")
	public String updatePassword(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			Model model) {
		UserEntity user = cartService.getUser();
		if (user == null) {
			return "redirect:/login";
		}

		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {

			model.addAttribute("passwordError", "Mật khẩu hiện tại không đúng");
			return "user/change-password.html";
		}

		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("passwordError", "Mật khẩu mới và xác nhận mật khẩu không khớp");
			return "user/change-password.html";
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		userJPA.save(user);

		return "redirect:/user/profile";
	}

}
