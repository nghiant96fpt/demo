package com.example.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // hàm xây dựng tất cả tham số
@NoArgsConstructor // hàm xây dựng không tham số
@Data // Getter/Setter
//@Setter
public class RegisterBean {
	private String username;
	private String password;
	private String email;
	private String fullName;

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
