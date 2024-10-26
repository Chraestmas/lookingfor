package com.lookingfor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lookingfor.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>{
	
	public Page<ItemEntity> findAllByTitleLikeAndCategory(String title, String category, Pageable p); 
	// findAllbyTitleLikeAndCategory("bottle", "ALL", 5페이지);
	public Page<ItemEntity> findAllByTitleLike(String title, Pageable p);
	public Page<ItemEntity> findAllByCategory(String category, Pageable p);
	public Page<ItemEntity> findAllBy(Pageable p);
	
}