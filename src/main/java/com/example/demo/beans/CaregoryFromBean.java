package com.example.demo.beans;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaregoryFromBean {
	private int id;

	@NotBlank(message = "Tên danh mục không rỗng")
	private String name;
}
