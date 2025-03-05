package com.fpoly.java5.controllers;

import com.fpoly.java5.entity.AddressEntity;
import com.fpoly.java5.entity.District;
import com.fpoly.java5.entity.Province;
import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.entity.Ward;
import com.fpoly.java5.jpas.AddressJPA;
import com.fpoly.java5.services.AddressService;
import com.fpoly.java5.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	AddressJPA addressJPA;

	@Autowired
	private CartService cartService;

	@GetMapping("/user/address-form")
	public String addressForm(@RequestParam(required = false) String idBack, Model model) {
		List<Province> provinces = addressService.getProvinces();
		model.addAttribute("backCheckOout", idBack);
		model.addAttribute("provinces", provinces);
		return "/user/edit-address"; 
	}

	@PostMapping("/user/address-form")
	public String addAddress(@RequestParam String name, @RequestParam String phone, 
	                         @RequestParam int province, @RequestParam int district, 
	                         @RequestParam int ward, @RequestParam(required = false) String idBack) {
	    UserEntity user = cartService.getUser();
	    AddressEntity addressEntity = new AddressEntity();
	    
	    addressEntity.setName(name);
	    addressEntity.setUser(user);
	    addressEntity.setPhone(phone);
	    
	    String provinceName = addressService.getProvinces().stream()
	                          .filter(p -> p.getCode() == province)
	                          .findFirst()
	                          .map(Province::getName)
	                          .orElse("Unknown");
	    
	    String districtName = addressService.getDistrictsByProvince(province).stream()
	                           .filter(d -> d.getCode() == district)
	                           .findFirst()
	                           .map(District::getName)
	                           .orElse("Unknown");
	    
	    String wardName = addressService.getWardsByDistrict(district).stream()
	                      .filter(w -> w.getCode() == ward)
	                      .findFirst()
	                      .map(Ward::getName)
	                      .orElse("Unknown");
	    
	    addressEntity.setAddress(provinceName + ", " + districtName + ", " + wardName);
	    addressJPA.save(addressEntity);

	    if (idBack != null) {
	        return "redirect:/user/checkout";
	    } else {
	        return "redirect:/user/profile";
	    }
	}

}

