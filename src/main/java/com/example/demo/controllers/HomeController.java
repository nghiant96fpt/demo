package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

//	@RequestMapping("/")
	// GET, POST, DELETE, PUT
//	tên path thành phần bên trong "" của các mapping 
//	- Phải là duy nhất theo Http Method 
	@GetMapping("/")
	public String index(Model model) {
//		Model => key, value
//		key sẽ tương ứng với tên biến trong phạm vi của 1 url 
		model.addAttribute("name", "Nguyen Van A");
		model.addAttribute("name", "Nguyen Van B");
		return "home.html";
	}

// Vòng đời khi user sử dụng Website Spring boot MVC
//	User = url => Controller = Xử lý yêu cầu 
//	=> Gửi dữ liệu vào Model => View sẽ nhận và hiển thị dữ luệu
//	User = url =>....

	@PostMapping("/")
	public String indexPost() {

		return "home.html";
	}
}
