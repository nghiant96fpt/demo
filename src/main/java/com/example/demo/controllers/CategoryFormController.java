package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.beans.CaregoryFromBean;
import com.example.demo.entities.Category;
import com.example.demo.jpa.CategoryJPA;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CategoryFormController {

	@Autowired
	CategoryJPA catJpa;

	@Autowired
	HttpServletResponse response;

	@GetMapping("/admin/category-form")
	public String categoryForm(Model model, @RequestParam(name = "id", defaultValue = "0") int id) {

		model.addAttribute("caregoryFromBean", new CaregoryFromBean());

		if (id > 0) {
//			sửa

			Optional<Category> optional = catJpa.findById(id);
			if (optional.isPresent()) {
				CaregoryFromBean bean = new CaregoryFromBean();
				bean.setName(optional.get().getName());
				bean.setId(optional.get().getId());

				model.addAttribute("caregoryFromBean", bean);
			}
		}

		return "category-form.html";
	}

	@PostMapping("/admin/category-form")
	public String categoryFormPost(@ModelAttribute(name = "caregoryFromBean") @Valid CaregoryFromBean bean,
			Errors errors, Model model) {

		if (!errors.hasErrors()) {
			Optional<Category> optional = catJpa.checkNameExist(bean.getName());

//			=> category id == 5, name == aaaaaa

			if (bean.getId() > 0) {
//				Sửa 

				if (!optional.isPresent() || (optional.isPresent() && optional.get().getId() == bean.getId())) {
					Category category = new Category();
					category.setName(bean.getName());
					category.setId(bean.getId());

					catJpa.save(category);

					try {
						response.sendRedirect("/admin/categories");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

//			Thêm 
			if (!optional.isPresent()) {
				Category category = new Category();
				category.setName(bean.getName());

				catJpa.save(category);
				try {
					response.sendRedirect("/admin/categories");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			model.addAttribute("errCat", "Tên danh mục đã tồn tại");
		}

		return "category-form.html";
	}
}
