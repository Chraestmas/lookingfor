package com.lookingfor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookingfor.entity.CategoryEntity;
import com.lookingfor.entity.ItemEntity;

@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity, Integer>{
	
	List<CategoryEntity> findAll(
			Integer id, 
			String name
	);

}
