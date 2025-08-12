package com.example.demo.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.CartItem;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.jpa.CartItemJPA;
import com.example.demo.jpa.ProductJPA;
import com.example.demo.jpa.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {

	@Autowired
	ProductJPA productJPA;

	@Autowired
	UserJPA userJPA;

	@Autowired
	CartItemJPA cartItemJPA;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@GetMapping("/user/add-to-cart")
	public String addToCart(@RequestParam(name = "prodId") int prodId) {

		Cookie[] cookies = request.getCookies();

		String userId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
		}

		Optional<CartItem> cartItemOptional = cartItemJPA.getCartItemByProdIdAndUserId(prodId,
				Integer.parseInt(userId));
//		Đã tồn tại sản phẩm 
		if (cartItemOptional.isPresent()) {
			int quantityNew = cartItemOptional.get().getQuantity() + 1;

			if (cartItemOptional.get().getProduct().getQuantity() >= quantityNew) {
				CartItem cartItem = cartItemOptional.get();
				cartItem.setQuantity(quantityNew);

				cartItemJPA.save(cartItem);
			}
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(1);
			Optional<Product> prodOptional = productJPA.findById(prodId);
			if (prodOptional.isPresent()) {
				cartItem.setProduct(prodOptional.get());
			}
			Optional<User> userOptional = userJPA.findById(Integer.parseInt(userId));
			if (userOptional.isPresent()) {
				cartItem.setUser(userOptional.get());
			}

			cartItemJPA.save(cartItem);
		}

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "home.html";
	}

	@GetMapping("/user/remove-cart-product")
	public String removeCartProduct(@RequestParam(name = "prodId") int prodId) {

		Cookie[] cookies = request.getCookies();

		String userId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
		}

		Optional<CartItem> cartItemOptional = cartItemJPA.getCartItemByProdIdAndUserId(prodId,
				Integer.parseInt(userId));

		if (cartItemOptional.isPresent()) {
			cartItemJPA.delete(cartItemOptional.get());
		}

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "home.html";
	}

	@GetMapping("/user/update-quantity-cart-product")
	public String updateQuantityCartProduct(@RequestParam(name = "prodId") int prodId,
			@RequestParam(name = "quantity") int quantity) {

		try {

			Cookie[] cookies = request.getCookies();

			String userId = null;

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userId")) {
					userId = cookie.getValue();
				}
			}

			Optional<CartItem> cartItemOptional = cartItemJPA.getCartItemByProdIdAndUserId(prodId,
					Integer.parseInt(userId));

			if (cartItemOptional.isPresent()) {
				if (quantity < 1) {
					cartItemJPA.delete(cartItemOptional.get());
				}

				if (quantity > 0 && cartItemOptional.get().getProduct().getQuantity() >= quantity) {
					CartItem cartItem = cartItemOptional.get();
					cartItem.setQuantity(quantity);

					cartItemJPA.save(cartItem);
				}

				response.sendRedirect("/");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "home.html";
	}

}
