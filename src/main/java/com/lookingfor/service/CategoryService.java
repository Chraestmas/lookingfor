package com.lookingfor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lookingfor.dto.CategoryDTO;
import com.lookingfor.dto.ItemDTO;
import com.lookingfor.entity.CategoryEntity;
import com.lookingfor.entity.ItemEntity;
import com.lookingfor.repository.CategoryRepository;

@Service
public class CategoryService {
	
	CategoryRepository categoryRepository;
	
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}


	//모든 카테고리 값을 가져오는 메소드 
	public List<CategoryDTO> getCategories() {
		
		List<CategoryEntity> res = null;
		CategoryDTO categoryDTO = new CategoryDTO();
		
		res = categoryRepository.findAll();
		
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		
		for(CategoryEntity categoryEntity : res) {
			categoryDTO.setId(categoryEntity.getId());
			categoryDTO.setName(categoryEntity.getName());
			categoryDTOList.add(categoryDTO);
		}//end of for
	
		return categoryDTOList;
	}
}
