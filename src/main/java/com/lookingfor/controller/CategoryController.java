package com.lookingfor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.lookingfor.dto.CategoryDTO;
import com.lookingfor.service.CategoryService;
//import com.lookingfor.response.PageResponse;

@Controller
@CrossOrigin(origins = "http://localhost:8089")
public class CategoryController {
	private CategoryService categoryService;
	
	
	public CategoryController(CategoryService categoryService){
		this.categoryService = categoryService;
	}
	
	//모든 category를 가져오는 api
	@GetMapping("/api/category")
	public ResponseEntity<List<CategoryDTO>> getCategories(){

		return ResponseEntity.status(200).body(categoryService.getCategories());
	}

}
