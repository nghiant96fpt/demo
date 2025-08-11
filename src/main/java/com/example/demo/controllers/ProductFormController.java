package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.beans.ProductFormBean;
import com.example.demo.entities.Category;
import com.example.demo.entities.Image;
import com.example.demo.entities.Product;
import com.example.demo.jpa.CategoryJPA;
import com.example.demo.jpa.ImageJPA;
import com.example.demo.jpa.ProductJPA;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class ProductFormController {

	@Autowired
	CategoryJPA categoryJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	ImageJPA imageJPA;

	@Autowired
	HttpServletResponse response;

	@GetMapping("/admin/product-form")
	public String productForm(Model model, @RequestParam(name = "id", defaultValue = "0") int id) {

		model.addAttribute("productBean", new ProductFormBean());
		List<Category> categories = categoryJPA.findAll();
		model.addAttribute("categories", categories);

		if (id > 0) {
//			sửa 
			Optional<Product> prodOptional = productJPA.findById(id);

			if (prodOptional.isPresent()) {
				ProductFormBean bean = new ProductFormBean();
				bean.setName(prodOptional.get().getName());
				bean.setDesc(prodOptional.get().getDesc());
				bean.setPrice(prodOptional.get().getPrice());
				bean.setQuantity(prodOptional.get().getQuantity());
				bean.setCatId(prodOptional.get().getCategory().getId());
				bean.setStatus(prodOptional.get().isStatus() ? 1 : 0);
				bean.setId(prodOptional.get().getId());

				model.addAttribute("productBean", bean);
			}
		}

		return "product-form.html";
	}

	@PostMapping("/admin/product-form")
	public String productFormPost(@ModelAttribute(name = "productBean") @Valid ProductFormBean bean, Errors errors,
			Model model) {

		try {
			List<Category> categories = categoryJPA.findAll();
			model.addAttribute("categories", categories);

//			Không có lỗi xảy ra 
			if (bean.getErrorImage() == null && !errors.hasErrors()) {
//				Trước khi lưu db
//				Lưu image vào project => trả về tên image => lưu tên vào db 

				Product product = new Product();
				product.setName(bean.getName());
				product.setDesc(bean.getDesc());
				product.setPrice(bean.getPrice());
				product.setQuantity(bean.getQuantity());
				product.setStatus(bean.getStatus() == 1 ? true : false);
				Optional<Category> catOptional = categoryJPA.findById(bean.getCatId());
				if (catOptional.isPresent()) {
					product.setCategory(catOptional.get());
				}

//				Sửa
				if (bean.getId() > 0) {
					product.setId(bean.getId());
				}

				Product productNew = productJPA.save(product);

//				Sửa
				if (bean.getId() > 0) {
					for (Image image : productNew.getImages()) {
						imageJPA.delete(image);
					}
				}

				for (MultipartFile image : bean.getImages()) {
					String name = this.uploadFile(image);

					Image imageEntity = new Image();
					imageEntity.setName(name);
					imageEntity.setProduct(productNew);

					imageJPA.save(imageEntity);
				}

				response.sendRedirect("/admin/products");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "product-form.html";
	}

	private String uploadFile(MultipartFile file) {
		Path filePath = Paths.get("images");
		try {
//			    	image.getContentType() => Dinh dang cua file upload
			Files.createDirectories(filePath);
			Date date = new Date();
			String fileName = date.getTime() + ".jpg";
			Files.copy(file.getInputStream(), filePath.resolve(fileName));

			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
