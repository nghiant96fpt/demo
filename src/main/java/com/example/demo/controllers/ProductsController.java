package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entities.Product;
import com.example.demo.jpa.ProductJPA;

@Controller
public class ProductsController {

	@Autowired
	ProductJPA productJPA;

	@GetMapping("/admin/products")
	public String products(Model model) {

		List<Product> products = productJPA.findAll();
		model.addAttribute("products", products);

		return "products.html";
	}
}
