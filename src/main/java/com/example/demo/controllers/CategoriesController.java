package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entities.Category;
import com.example.demo.jpa.CategoryJPA;

@Controller
public class CategoriesController {

	@Autowired
	CategoryJPA categoryJPA;

	@GetMapping("/admin/categories")
	public String categories(Model model) {

		List<Category> categories = categoryJPA.findAll();

		model.addAttribute("categories", categories);

		return "categories.html";
	}
}
