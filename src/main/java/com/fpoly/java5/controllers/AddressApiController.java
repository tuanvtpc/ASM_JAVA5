package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.java5.entity.District;
import com.fpoly.java5.entity.Ward;
import com.fpoly.java5.services.AddressService;

@RestController
@RequestMapping("/api")
public class AddressApiController {

	@Autowired
	private AddressService addressService;

	@GetMapping("/districts")
	public List<District> getDistricts(@RequestParam int provinceCode) {
		return addressService.getDistrictsByProvince(provinceCode); 
	}

	@GetMapping("/wards")
	public List<Ward> getWards(@RequestParam int districtCode) {
		return addressService.getWardsByDistrict(districtCode); 
	}
}