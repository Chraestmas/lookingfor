package com.lookingfor.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lookingfor.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>{
	
	
	// jpql을 활용하여 조건 검색하기 
//	@Query("select a.*, b.name as category_Name, c.name as location_Name, d.name as admin_Name"
//			+ "from item a, category b, location c, user d"
//			+ "where a.category_id = b.id"
//			+ "and a.location_id = c.id"
//			+ "and a.user_id = d.id"
//			+ "and a.found_yn in (:foundYn)"
//			+ "and a.category_id in (:categoryId)"
//			+ "and a.name like '%:itemName%'")
//	public Page<ItemEntity> findAllByFoundYnInAndCategoryIdInAndNameLike(
//			@Param("foundYn") char[] foundYn, 
//			@Param("categoryId") Integer[] categoryId, 
//			@Param("itemName") String itemName);
	
	
		Page<ItemEntity> findAllByFoundYnInAndCategory_IdInAndNameIgnoreCaseContaining(
				List<Character> foundYns,
				List<Integer> categoryIds, 
				String name,
				Pageable p
		);
	
	
	
	
	/*
	// jpql을 활용하여 조건 검색하기 
	@Query("SELECT mm "
			+ "FROM MeetingMembers AS mm "
			+ "WHERE mm.meeting.meetingId = :meetingId "
			+ "AND mm.user.userEmail NOT IN ("
			+ 	"SELECT mc.user.userEmail FROM MissionCompletions AS mc "
			+ 	"WHERE mc.meeting.meetingId = :meetingId "
			+ 	"AND mc.completionDate = :completionDate"
			+ ")")
	
	public List<MeetingMembers> getIncompletedMembers(
			@Param("meetingId") int meetingId, 
			@Param("completionDate") LocalDate completionDate);
	*/
	
	
	//public Page<ItemEntity> findAllByTitleLikeAndCategory(String itemName, String categoryId, Pageable p); 
	// findAllbyTitleLikeAndCategory("bottle", "ALL", 5페이지);
	
	//public Page<ItemEntity> findAllByItemNameLike(String itemName, Pageable p);
	//public Page<ItemEntity> findAllByCategoryId(String categoryId, Pageable p);
	//public Page<ItemEntity> findAllBy(Pageable p);
	
}