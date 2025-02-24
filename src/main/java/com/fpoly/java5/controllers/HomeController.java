package com.fpoly.java5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fpoly.java5.jpas.ProductJPA;

@Controller
public class HomeController {
	
	@GetMapping("/homepage")
	public String homePage() {
		return "/user/index.html";
	}
	
	@Autowired
	ProductJPA productJPA;
}
