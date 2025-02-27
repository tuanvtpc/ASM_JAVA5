package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java5.entity.Province;
import com.fpoly.java5.services.AddressService;

@RestController // restController dùng để điểu khiển các phương thức liên quan tới API
@RequestMapping("/api/provinces")
public class ProvinceController {
	
	@Autowired
	AddressService addressService;
	
    @GetMapping("/edit-address")
    public String showDropdown(Model model) {
        List<Province> provinces = addressService.getProvinces();
        model.addAttribute("provinces", provinces);
        return "/user/profile.html";
    }

    
}
