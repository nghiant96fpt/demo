package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.RegisterBean;
import com.example.demo.entities.User;
import com.example.demo.jpa.UserJPA;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

	@Autowired
	UserJPA userJPA;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;
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

		if (!errors.hasErrors()) {

			if (userJPA.checkEmailOrUserExist(bean.getUsername(), bean.getEmail()).size() == 0) {
				User user = new User();
				user.setUsername(bean.getUsername());
				user.setEmail(bean.getEmail());
				user.setPassword(bean.getPassword());
				user.setName(bean.getFullName());
				user.setRole(1); // 1 == user
				user.setStatus(true);

//				hàm save sẽ thực 2 công việc cả thêm và sửa 
//				Nếu entity không có id => Thêm 
//				Nếu entity có id => Sửa
				userJPA.save(user);
//				chuyển về trang đăng nhập 
				try {
					response.sendRedirect("/login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			model.addAttribute("errRegister", "Email hoặc tên tài khoản đã tồn tại");
		}

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
