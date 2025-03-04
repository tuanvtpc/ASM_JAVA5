package com.fpoly.java5.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.java5.beans.ProductBean;
import com.fpoly.java5.entity.CategoryEntity;
import com.fpoly.java5.entity.ProductEntity;
import com.fpoly.java5.jpas.CategoryJPA;
import com.fpoly.java5.jpas.ProductJPA;
import com.fpoly.java5.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

	@Autowired
	CategoryJPA categoryJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	ProductService productService;

	@GetMapping("/admin/product")
	public String product(Model model, @RequestParam(name = "search", required = false) String search) {
		if (search != null && !search.isEmpty()) {
			List<ProductEntity> productList = productService.searchProductsByName(search);
			model.addAttribute("productList", productList);
		} else {
			model.addAttribute("productList", productJPA.findAll());
		}
		return "/admin/product.html";
	}

	@ModelAttribute(name = "productList")
	public List<ProductEntity> getProduct() {
		return productJPA.findAll();
	}

	@ModelAttribute(name = "categories")
	public List<CategoryEntity> getCategory() {
		return categoryJPA.findAll();
	}

	@GetMapping("/admin/product-form")
	public String showFormProduct(Model model, @RequestParam("id") Optional<Integer> id,
			@ModelAttribute("errorMessage") String errorMessage,
			@ModelAttribute("successMessage") String successMessage) {

		ProductBean bean = new ProductBean();

		if (id.isPresent()) {
			Optional<ProductEntity> productOptional = productJPA.findById(id.get());
			if (productOptional.isPresent()) {
				ProductEntity product = productOptional.get();
				bean.setId(id);
				bean.setName(product.getName());
				bean.setDesc(product.getDescription());
				bean.setPrice(product.getPrice());
				bean.setQuantitty(product.getQuantity());
				bean.setCat_id(product.getCategory().getId());

				model.addAttribute("currentImages", product.getImages());
			}
		}

		if (!errorMessage.isEmpty())
			model.addAttribute("errorMessage", errorMessage);
		if (!successMessage.isEmpty())
			model.addAttribute("successMessage", successMessage);

		model.addAttribute("product", bean);
		return "/admin/product-form.html";
	}

	@PostMapping("/admin/product-form")
	public String handleProductForm(@Valid @ModelAttribute("product") ProductBean productBean, Errors errors,
			Model model, RedirectAttributes redirectAttributes) {

		boolean hasErrors = errors.hasErrors();
		String imageError = productBean.isImagesError();

		if (hasErrors || imageError != null) {
			if (imageError != null) {
				model.addAttribute("imageError", imageError);
			}
			return "/admin/product-form.html";
		}

		Boolean result;

		if (productBean.getId() != null && productBean.getId().isPresent()) {
			result = productService.updateProduct(productBean.getId().get(), productBean);
		} else {
			result = productService.saveProduct(productBean);
		}

		if (result) {
			redirectAttributes.addFlashAttribute("successMessage", "Lưu sản phẩm thành công!");
			return "redirect:/admin/product";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Lưu sản phẩm thất bại!");
			return "redirect:/admin/product-form?id=" + productBean.getId().orElse(null);
		}
	}

	@PostMapping("/admin/delete-product")
	public String deleteUser(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {

		boolean delete = productService.deleteProduct(id);

		if (delete) {
			redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Xóa sản phẩm thất bại!");
		}

		return "redirect:/admin/product";
	}
	
	@PostMapping("/admin/update-product-price")
	public String updateProductPrice(@RequestParam("id") int id,
	                                 @RequestParam("price") int price,
	                                 RedirectAttributes redirectAttributes) {
	    Optional<ProductEntity> productOptional = productJPA.findById(id);
	    if (productOptional.isPresent()) {
	        ProductEntity product = productOptional.get();
	        product.setPrice(price);
	        productJPA.save(product);
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giá thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
	    }
	    return "redirect:/admin/product";
	}

	@PostMapping("/admin/update-product-quantity")
	public String updateProductQuantity(@RequestParam("id") int id,
	                                    @RequestParam("quantity") int quantity,
	                                    RedirectAttributes redirectAttributes) {
	    Optional<ProductEntity> productOptional = productJPA.findById(id);
	    if (productOptional.isPresent()) {
	        ProductEntity product = productOptional.get();
	        product.setQuantity(quantity);
	        productJPA.save(product);
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số lượng thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
	    }
	    return "redirect:/admin/product";
	}

	@PostMapping("/admin/update-product-status")
	public String updateProductStatus(@RequestParam("id") int id,
	                                  @RequestParam("isActive") boolean isActive,
	                                  RedirectAttributes redirectAttributes) {
	    Optional<ProductEntity> productOptional = productJPA.findById(id);
	    if (productOptional.isPresent()) {
	        ProductEntity product = productOptional.get();
	        product.setActive(isActive);
	        productJPA.save(product);
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm!");
	    }
	    return "redirect:/admin/product";
	}

}
