package com.lookingfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lookingfor.entity.PictureEntity;

public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {
	
	
	public List<PictureEntity> findAllByItemId(Integer id);
}
