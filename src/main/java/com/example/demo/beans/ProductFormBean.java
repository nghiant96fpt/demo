package com.example.demo.beans;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFormBean {
	@NotBlank(message = "Tên sản phẩm không được bỏ trống")
	private String name;

	@NotBlank(message = "Mô tả không được bỏ trống")
	@Length(min = 10, max = 200, message = "Mô tả phải có ít nhất 10 - 200 ký tự")
	private String desc;

//	với kiểu số, boolean, array, object
//	Không sử dụng được NotBlank, NotEmpty, Length
	@NotNull(message = "Giá không được bỏ trống")
	@Min(value = 1000, message = "Giá phải từ 1.000 VNĐ")
	private int price;

	@NotNull(message = "Số lượng không được bỏ trống")
	@Min(value = 1, message = "Số lượng phải từ 1")
	private int quantity;
}

//- Tên không được bỏ trống
//- Mô tả không được bỏ trống
//- Mô tả phải có ít nhất 10 ký tự và tối đa 200 ký tự
//- Giá phải lớn hơn 1000
//- Số lượng phải lớn hơn 0