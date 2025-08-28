package com.example.demo.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckOutBean {
	@NotBlank(message = "Tên người nhận không trống")
	private String name;

	@NotBlank(message = "Số điện thoại người nhận không trống")
	@Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không đúng định dạng")
	private String phone;

	@NotBlank(message = "Địa chỉ người nhận không trống")
	private String address;
}
