package com.lookingfor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lookingfor.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
//	public Page<Item> findAllByTitleLikeAndCategory(String title, String category, Pageable p);
//	public Page<Item> findAllByTitleLike(String title, Pageable p);
//	public Page<Item> findAllByCategory(String category, Pageable p);
//	public Page<Item> findAllBy(Pageable p);

}


////
/*
package com.jaksim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jaksim.entity.JaksimMeetings;

public interface JaksimMeetingsRepository extends JpaRepository<JaksimMeetings, Integer>{
	
	// findAllbyTitleLikeAndCategory("안녕", "ALL", 5페이지);
	public Page<JaksimMeetings> findAllByTitleLikeAndCategory(String title, String category, Pageable p);
	public Page<JaksimMeetings> findAllByTitleLike(String title, Pageable p);
	public Page<JaksimMeetings> findAllByCategory(String category, Pageable p);
	public Page<JaksimMeetings> findAllBy(Pageable p);
}
*/
