package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jpa.CategoryJPA;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CategoryDeleteController {

	@Autowired
	HttpServletResponse response;

	@Autowired
	CategoryJPA categoryJPA;

	@PostMapping("/admin/category-delete")
	public String categoryDelete(@RequestParam(name = "id") int id) {

		try {
			categoryJPA.deleteById(id);

			response.sendRedirect("/admin/categories");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "categories.html";
	}
}
