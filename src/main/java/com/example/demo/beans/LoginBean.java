package com.example.demo.beans;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginBean {
	@NotBlank(message = "Tên tài khoản không được rỗng")
	private String username;

	@NotBlank(message = "Mật khẩu khoản không được rỗng")
	private String password;
}
