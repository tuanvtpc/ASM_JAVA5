package com.fpoly.java5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IntroduceController {
	@GetMapping("/introduce")
	public String introduce() {
		return "/user/introduce.html";
	}
	
}
