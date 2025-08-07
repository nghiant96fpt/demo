package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.RegisterBean;

import jakarta.servlet.http.HttpServletRequest;

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

	public String registerPost(RegisterBean bean, Model model) {
//		System.out.println(bean.getUsernameTest());

		model.addAttribute("registerBean", bean);

		return "register.html";
	}
}
