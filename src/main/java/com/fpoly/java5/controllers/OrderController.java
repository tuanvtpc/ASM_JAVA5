package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fpoly.java5.entity.OrderDetailEntity;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.jpas.OrderDetailJPA;
import com.fpoly.java5.jpas.OrderJPA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {
	@Autowired
	OrderJPA orderJPA;

	@Autowired
	OrderDetailJPA orderDetailJPA;

	public List<OrderEntity> getListOrder() {
		return orderJPA.findAll();
	}

	@GetMapping("/admin/order")
	public String orderLayout(@RequestParam(name = "status", required = false) Integer status, Model model) {
	    List<OrderEntity> orders;
	    if (status != null) {
	        orders = orderJPA.findByStatus(status);
	    } else {
	        orders = getListOrder();
	    }
	    model.addAttribute("order", orders);
	    return "/admin/order.html";
	}

	@GetMapping("/admin/order-detail")
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
		return "admin/order-detail.html";
	}

	@PostMapping("/admin/update-statusOrder")
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
		return "redirect:/admin/order";
	}

	private boolean isValidStatus(int currentStatus, int newStatus) {
		switch (currentStatus) {
		case 0: 
			return newStatus == 1 || newStatus == 6; // chỉ có thể chuyển xác nhận hoặc hủy
		case 1: // Đã xác nhận
			return newStatus == 2 || newStatus == 6; //chỉ có thể chuyển sang đang giao hoặc hủy đơn
		case 2: // Đang giao
			return newStatus == 3 || newStatus == 6; // chỉ có thể chuyển sang đã giao hoặc hủy
		case 3: // Đã giao
			return newStatus == 4; // đã giao thỉ chỉ có cập nhật sang đã nhận
		case 4: // đã nhận
			return false; 
		case 5: // Trả hàng
			return false; 
		case 6: // Hủy đơn
			return false; 
		default:
			return false;
		}
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

}
