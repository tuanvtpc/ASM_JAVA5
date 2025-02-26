package com.fpoly.java5.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.java5.beans.CategoryBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.jpas.CategoryJPA;

import jakarta.validation.Valid;

@Controller
public class CategoryController {

	@Autowired
	private CategoryJPA categoryJPA; // Inject Repository vào Controller

	// Hiển thị danh sách danh mục
	@GetMapping("/admin/category")
	public String listCategories(@RequestParam(name = "keyword", required = false) String keyword, Model model) {

		return "admin/category";
	}

}
