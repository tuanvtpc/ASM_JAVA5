package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java5.services.AddressService;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {
	
	private final AddressService addressService;
	
	 @GetMapping("/form")
	    public String showForm(Model model) {
	        List<Map<String, Object>> provinces = addressService.getProvinces();
	        model.addAttribute("provinces", provinces);
	        return "/user/profile.html";
	    }

    public ProvinceController(AddressService provinceService) {
        this.addressService = provinceService;
    }

    @GetMapping
    public List<Map<String, Object>> getAllProvinces() {
        return addressService.getProvinces();
    }
}
