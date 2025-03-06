package com.fpoly.java5.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fpoly.java5.entity.OrderEntity;
import com.fpoly.java5.jpas.OrderJPA;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.OrderService;

@Controller
public class DashboardController {
	@Autowired
	OrderJPA orderJPA;
	
	@Autowired
    OrderService orderService;
	
	@Autowired
	UserJPA userJPA;
	
	@GetMapping("/admin/dashboard")
    public String dashBoard(Model model) {
        int countOrder = orderJPA.countAllOrders();
        Double totalAmount = orderService.getTotalAmountByStatus();
        int totalUser = userJPA.countAllUsers();
        int totalOrderStatus = orderJPA.countAllOrderByStatus();
        List<OrderEntity> orderList = orderJPA.findAll();

      

        model.addAttribute("orderList", orderList);
        model.addAttribute("totalOrderStatus", totalOrderStatus);
        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("countOrder", countOrder);

        return "/admin/dashboard.html";
    }
}
