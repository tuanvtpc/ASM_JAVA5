package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DetailProductController {

	@GetMapping("/detail-product")
	public String detailProduct() {

		return "/user/detail-product.html";
	}

}
