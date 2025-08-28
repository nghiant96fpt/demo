package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.CheckOutBean;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.jpa.CartItemJPA;
import com.example.demo.jpa.OrderItemJPA;
import com.example.demo.jpa.OrderJPA;
import com.example.demo.jpa.ProductJPA;
import com.example.demo.jpa.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CheckOutController {

	@Autowired
	CartItemJPA cartItemJPA;

	@Autowired
	OrderJPA orderJPA;

	@Autowired
	OrderItemJPA orderItemJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	UserJPA userJPA;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@GetMapping("/user/check-out")
	public String checkOut(Model model) {

		Cookie[] cookies = request.getCookies();

		String userId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
		}

		List<CartItem> cartItems = cartItemJPA.getCartItemByUserId(Integer.parseInt(userId));

		int totalPrice = 0;

		for (CartItem cartItem : cartItems) {
			totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("checkOutBean", new CheckOutBean());

		return "check-out.html";
	}

	@PostMapping("/user/check-out")
	public String checkOutPost(@ModelAttribute(name = "checkOutBean") @Valid CheckOutBean bean, Errors errors,
			Model model) {

		Cookie[] cookies = request.getCookies();

		String userId = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
			}
		}

		List<CartItem> cartItems = cartItemJPA.getCartItemByUserId(Integer.parseInt(userId));

		int totalPrice = 0;

		for (CartItem cartItem : cartItems) {
			totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);

		if (!errors.hasErrors()) {
//			Kiểm tra số lượng sản phẩm trong db và trong giỏ hàng có mua được ko
			for (CartItem cartItem : cartItems) {
				if (cartItem.getQuantity() > cartItem.getProduct().getQuantity()) {

					model.addAttribute("checkOutErr", "Số lượng sản phẩm không đủ");

					return "check-out.html";
				}
			}
//			Lấy userId từ cookie
//			Lấy user entity từ user id 
			Optional<User> userOptional = userJPA.findById(Integer.parseInt(userId));
//			Tạo đơn hàng insert order => order id
			if (userOptional.isPresent()) {
				Order order = new Order(); // không có id
				order.setName(bean.getName());
				order.setPhone(bean.getPhone());
				order.setAddress(bean.getAddress());
				order.setUser(userOptional.get());
				order.setTotalPrice(totalPrice);
				order.setStatus(0);

//				0 = Chờ xác nhận (có thể huỷ ở phía admin và user)
//				1 = Đã xác nhận (có thể huỷ ở phía admin)
//				2 = Đã giao hàng (không thể huỷ)
//				3 = Đã hoàn thành (không thể huỷ)
//				4 = Huỷ đơn
//				Trạng thái đơn hàng chỉ tăng không giảm

				Order orderNew = orderJPA.save(order); // có id

//				Tạo order Item => xoá cartid tương ứng và cập nhật số lượng sp ở db	
				for (CartItem cartItem : cartItems) {
					OrderItem orderItem = new OrderItem();
					orderItem.setOrder(orderNew);
					orderItem.setQuantity(cartItem.getQuantity());
					orderItem.setPrice(cartItem.getProduct().getPrice());
//					tạo order item tưng ứng với giỏ hàng
					orderItemJPA.save(orderItem);

//					Cập nhật số lượng sản phẩm trong db  
					Product product = cartItem.getProduct();
					product.setQuantity(product.getQuantity() - cartItem.getQuantity());
					productJPA.save(product);

					cartItemJPA.delete(cartItem);
				}

				try {
					response.sendRedirect("/");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return "check-out.html";
	}
}
