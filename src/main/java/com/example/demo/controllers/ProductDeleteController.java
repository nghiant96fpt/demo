package com.example.demo.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Image;
import com.example.demo.entities.Product;
import com.example.demo.jpa.ImageJPA;
import com.example.demo.jpa.ProductJPA;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ProductDeleteController {

	@Autowired
	HttpServletResponse response;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	ImageJPA imageJPA;

	@PostMapping("/admin/product-delete")
	public String deleteProduct(@RequestParam(name = "id") int id) {
		try {
			Optional<Product> prodOptional = productJPA.findById(id);

			if (prodOptional.isPresent()) {
				if (prodOptional.get().getOrderItems().size() > 0 || prodOptional.get().getCartItemts().size() > 0) {
					response.sendRedirect("/admin/products");
					return null;
				}

				for (Image image : prodOptional.get().getImages()) {
					imageJPA.delete(image);
				}

				productJPA.delete(prodOptional.get());
			}

			response.sendRedirect("/admin/products");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "products.html";
	}
}
