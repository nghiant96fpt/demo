package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.beans.LoginBean;
import com.example.demo.entities.User;
import com.example.demo.jpa.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {

	@Autowired
	UserJPA userJPA;

	@Autowired
	HttpServletResponse response;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginBean", new LoginBean());

		return "login.html";
	}

	@PostMapping("/login")
	public String loginPost(@ModelAttribute(name = "loginBean") @Valid LoginBean bean, Errors errors, Model model) {

		if (!errors.hasErrors()) {
			Optional<User> optional = userJPA.checkUsernameExist(bean.getUsername());
			if (optional.isPresent()) {
				if (optional.get().getPassword().equals(bean.getPassword())) {
					try {
//						Lưu userId và role vào cookie 
//						Lưu để sử dụng cho những chức năng có user id
//						dùng kiểm tra vai trò user có thể dùng được chức năng nào?

//						lưu cookie thì dùng response 
//						lấy cookie ra dùng request 

						Cookie cookieUserId = new Cookie("userId", String.valueOf(optional.get().getId()));
//						cookieUserId.setMaxAge(60 * 60 * 24 * 3) => giây
						Cookie cookieRole = new Cookie("role", String.valueOf(optional.get().getRole()));

						response.addCookie(cookieUserId);
						response.addCookie(cookieRole);

						if (optional.get().getRole() == 0) {
							response.sendRedirect("/admin/products");
							return null;
						} else {
							response.sendRedirect("/");
							return null;
						}
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
			}

			model.addAttribute("errLogin", "Sai tên đăng nhập hoặc mật khẩu");
		}
		return "login.html";
	}
}

// User -> C -> M -> V -> User 
// User -> Middle -> C -> Middle -> M -> V -> User
