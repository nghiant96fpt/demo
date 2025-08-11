package com.example.demo.beans;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

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
	private int id;

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

	@NotNull(message = "Danh mục bắt buộc chọn")
	@Min(value = 1, message = "Danh mục bắt buộc chọn")
	private int catId;

	private int status;

	private List<MultipartFile> images;

	public String getErrorImage() {
//		Bắt buộc phải upload ít nhất 1 file ảnh 
//		Tất cả các file khi upload lên bắt buộc phải là ảnh 
//		type == image/*
//		Kích thước mỗi file không được vượt quá 20MB 

		if (images == null) {
			return null;
		}

		if (this.images.size() == 0) {
			return "Bạn chưa chọn ảnh";
		}
		for (MultipartFile image : this.images) {
//			Tuỳ theo loại file mà trả về các định dạng
//			- Image: image/jpg, image/png, image/webp,...
//			- Docs: application/docx, application/pdf,....
			if (!image.getContentType().startsWith("image/")) {
				return "File upload bắt buộc phải là ảnh";
			}

			long maxSize = 1024 * 1024;

//			image.getSize() => byte 
			if (image.getSize() > maxSize) {
				return "Kích thước file vượt quá kích thước tối đa";
			}
		}

//		Không có lỗi 
		return null;
	}
}

//- Tên không được bỏ trống
//- Mô tả không được bỏ trống
//- Mô tả phải có ít nhất 10 ký tự và tối đa 200 ký tự
//- Giá phải lớn hơn 1000
//- Số lượng phải lớn hơn 0