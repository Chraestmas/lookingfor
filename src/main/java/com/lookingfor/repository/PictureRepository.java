package com.lookingfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lookingfor.entity.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
	
	
	public List<Picture> findAllByItemId(Integer id);
}
