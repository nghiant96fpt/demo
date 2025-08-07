package com.example.demo.beans;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // hàm xây dựng tất cả tham số
@NoArgsConstructor // hàm xây dựng không tham số
@Data // Getter/Setter
//@Setter
public class RegisterBean {
	@NotBlank(message = "Tên tài khoản không được trống") // (" " => fail)
	private String username;

	@NotBlank(message = "Mật khẩu không được bỏ trống")
	@Length(min = 8, max = 20, message = "Mật khẩu tối thiểu 8 - 20 ký tự")
	private String password;

	@NotBlank(message = "Email không được bỏ trống")
	@Email(message = "Email không đúng định dạng")
	private String email;

	@NotBlank(message = "Họ và tên không được bỏ trống")
	private String fullName;

//	@NotNull bắt buộc phải có giá trị không null (API) 
//	@NotEmpty bắt buộc phải có ít nhất 1 ký tự (" " => pass)
//	@Min, @Max giá trị nhỏ hoặc lớn nhất (int)
//	@Range(max, min) giá trị nhỏ và lớn nhất
//	@DecimalMax, @DecimalMin giá trị nhỏ hoặc lớn nhất (float, double)

//	public String getUsernameTestEmail() {
//		return username;
//	}
}

// Sử dụng được validate ở trong bean class 

//registerBean.username hiển thị ở html bằng th
// Không phải lấy giá trị từ username
// mà giá trị của nó lấy từ hàm Getter của username
// getUsername()
// Loại bỏ chữ get => Chuyển tên còn lại
// phía sau chữ get về đúng định dang biến java 
