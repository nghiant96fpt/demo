package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Order;
import com.example.demo.jpa.OrderJPA;
import com.example.demo.jpa.ProductJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class OrderController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	OrderJPA orderJPA;

	@Autowired
	ProductJPA productJPA;

	@PostMapping("/user/cancel-order")
	public String cancelOrderUser(@RequestParam(name = "order_id") int orderId) {

		Cookie[] cookies = request.getCookies();

		String userId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
		}

		Optional<Order> orderOptional = orderJPA.getBorderByIdAndUserId(orderId, Integer.parseInt(userId));

		if (orderOptional.isPresent()) {
			if (orderOptional.get().getStatus() == 0) {
//				Huỷ đơn 
				Order order = orderOptional.get();
				order.setStatus(4);
				orderJPA.save(order);
			}
		}

		return "home.html";
	}

	@PostMapping("/admin/cancel-order")
	public String cancelOrderAdmin(@RequestParam(name = "order_id") int orderId) {

		Optional<Order> orderOptional = orderJPA.findById(orderId);

		if (orderOptional.isPresent()) {
			if (orderOptional.get().getStatus() == 0 || orderOptional.get().getStatus() == 1) {
//				Huỷ đơn 
				Order order = orderOptional.get();
				order.setStatus(4);
				orderJPA.save(order);
			}
		}

		return "home.html";
	}

	@PostMapping("/admin/update-order")
	public String updateStatusOrderAdmin(@RequestParam(name = "order_id") int orderId,
			@RequestParam(name = "status") int status) {

		if (status == 4) {
//			không cho thực hiện 
			return "home.html";
		}

		Optional<Order> orderOptional = orderJPA.findById(orderId);

		if (orderOptional.isPresent()) {
			if (orderOptional.get().getStatus() < status && status - orderOptional.get().getStatus() == 1) {
				Order order = orderOptional.get();
				order.setStatus(status);
				orderJPA.save(order);
			}
		}

		return "home.html";
	}
}
