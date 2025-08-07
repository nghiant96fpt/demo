package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.RegisterBean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

	@Autowired
	HttpServletRequest request;
// Không có @Autowired => request = null 

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("registerBean", new RegisterBean());
		return "register.html";
	}
//  http://localhost:8080/register?username=abc&password=1234&....

	@PostMapping("/register")
//	public String registerPost(
//			@RequestParam(name = "username") String username,
//			@RequestParam(name = "password") String password,
//			@RequestParam(name = "email") String email,
//			@RequestParam(name = "fullName") String fullName) {

//	public String registerPost() {
//
//		String username = request.getParameter("username");
//		Khi dùng HttpServletRequest có 1 vấn đề
//		Tất cả các giá trị khi được lấy ra đều là kiểu String 

	public String registerPost(Model model, @ModelAttribute(name = "registerBean") @Valid RegisterBean bean,
			Errors errors) {
//		System.out.println(bean.getUsernameTest());
//		Nhận dữ liệu thông qua Bean Class
//		model.addAttribute("registerBean", bean);
//		Gửi Bean Class trở về view để hiển thị lại 

//		Kiểm tra lỗi của các ô input trong form 
//		- Username => Không được bỏ trống 
//		- Password => Ít nhất 8 ký tự
//		- Email => Đúng định dạng
//		- FulName => Không được bỏ trống 

		return "register.html";
	}
}

// User => url ở browser => Get mapping active
// => giao diện html => click button => post mapping active 
// => gửi model => giao diện html 
