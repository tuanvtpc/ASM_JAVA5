package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.entity.UserEntity;
import com.fpoly.java5.jpas.UserJPA;
import com.fpoly.java5.services.UserService;



@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired

	UserJPA userjpa;

	@GetMapping("/admin/user")
	public String users(Model model) {

		List<UserEntity> userEntities = userjpa.findAll().stream().filter(user -> user.getRole() != 0).toList();

		model.addAttribute("users", userEntities);

		return "/admin/user.html";
	}
	@GetMapping("/admin/search")
	public String searchUsers(
	    @RequestParam("keyword") String keyword, 
	    @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
	    Model model) {

	    boolean asc = sort.equalsIgnoreCase("asc");
	    List<UserEntity> users = userService.searchUsers(keyword, asc);
	    model.addAttribute("users", users);
	    return "/admin/user.html";
	}


}
