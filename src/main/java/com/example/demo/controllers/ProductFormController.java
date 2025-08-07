package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.ProductFormBean;

import jakarta.validation.Valid;

@Controller
public class ProductFormController {

	@GetMapping("/admin/product-form")
	public String productForm(Model model) {

		model.addAttribute("productBean", new ProductFormBean());

		return "product-form.html";
	}

	@PostMapping("/admin/product-form")
	public String productFormPost(@ModelAttribute(name = "productBean") @Valid ProductFormBean bean, Errors errors) {

		return "product-form.html";
	}
}
