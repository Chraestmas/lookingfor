package com.lookingfor.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.jaksim.entity.JaksimMeetings;
import com.lookingfor.dto.ItemDTO;
import com.lookingfor.dto.PictureDTO;
import com.lookingfor.entity.CategoryEntity;
import com.lookingfor.entity.ItemEntity;
import com.lookingfor.entity.PictureEntity;
import com.lookingfor.repository.CategoryRepository;
import com.lookingfor.repository.ItemRepository;
import com.lookingfor.repository.PictureRepository;
import com.lookingfor.response.PageResponse;

@Service
public class ItemService {
	
	ItemRepository itemRepository;
	PictureRepository pictureRepository;
	CategoryRepository categoryRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository, PictureRepository pictureRepository, 
			CategoryRepository categoryRepository) {
		this.itemRepository = itemRepository;
		this.pictureRepository = pictureRepository;
		this.categoryRepository = categoryRepository;
	}
	
	//item을 id로 찾는 메소
	public ItemDTO getItemById(Integer id) {
		ItemDTO itemDTO = new ItemDTO();
		
		Optional<ItemEntity> optionalItem = itemRepository.findById(id);
		if(!optionalItem.isPresent()) {
			return itemDTO; // 해당 id의 item이 없음
		}
		
		ItemEntity itemEntity = optionalItem.get();
		
		itemDTO.setId(itemEntity.getId());
		itemDTO.setName(itemEntity.getName());
		itemDTO.setCategoryId(itemEntity.getCategoryId());
		itemDTO.setFoundDate(itemEntity.getFoundDate());
		itemDTO.setNameTag(itemEntity.getNameTag());
		itemDTO.setLocationId(itemEntity.getLocationId());
		itemDTO.setFoundYn(itemEntity.getFoundYn());
		itemDTO.setPickupDate(itemEntity.getPickupDate());
		itemDTO.setPickupPersonName(itemEntity.getPickupPersonName());
		itemDTO.setDescription(itemEntity.getDescription());
		itemDTO.setUserId(itemEntity.getUserId());

		List<PictureEntity> pictureEntityList = pictureRepository.findAllByItemId(id);
		
		List<PictureDTO> pictureDTOList = new ArrayList<>();
		for(PictureEntity pictureEntity : pictureEntityList) {
			PictureDTO pictureDTO = new PictureDTO();
			pictureDTO.setId(pictureEntity.getId());
			pictureDTO.setUrl(pictureEntity.getUrl());
			
			pictureDTOList.add(pictureDTO);
		}
		
		itemDTO.setPictures(pictureDTOList);
		
		return itemDTO;
	} // end of getItemById
	
	
	public PageResponse<ItemDTO> getItems(int page, int size, List<Character> foundYn, List<Integer> categoryId, String itemName) {
		
		Sort s = Sort.by(Sort.Order.desc("foundDate"));
		PageRequest pr = PageRequest.of(page-1, size, s);
		
		Page<ItemEntity> res = null;
		
		List<Character> foundYns = foundYn; 
		if(foundYn == null) { // 전체조회
			foundYns = new ArrayList<>();
			foundYns.add('N');
			foundYns.add('Y');
		}
		
		List<Integer> categoryIds = categoryId;
		if(categoryId == null) {
			List<CategoryEntity> categorys = categoryRepository.findAll();
			categoryIds = categorys.stream().map(CategoryEntity::getId).collect(Collectors.toList());
		}
		
		if(itemName == null) {
			itemName ="%%";
		}else {
			itemName = "%" + itemName + "%";
		}
		
		res = itemRepository.findAllByFoundYnInAndCategory_IdInAndNameLike(foundYns, categoryIds, itemName, pr);
		
		// res.getContent() --> 리스트<ItemEntity>  --> 리스트<ItemDTO>
		// ItemEntity를 DTO로 바꿔주고 PageResponse에다 담아서 Controller로 전달
		List<ItemDTO> itemDTOList = new ArrayList<>();
		
		for(ItemEntity itemEntity : res.getContent()) {
			ItemDTO itemDTO = new ItemDTO();			
			itemDTO.setId(itemEntity.getId());
			itemDTO.setName(itemEntity.getName());
			itemDTO.setCategoryId(itemEntity.getCategory().getId());
			itemDTO.setCategoryName(itemEntity.getCategory().getName());
			itemDTO.setFoundDate(itemEntity.getFoundDate());
			itemDTO.setNameTag(itemEntity.getNameTag());
			itemDTO.setLocationId(itemEntity.getLocation().getId());
			itemDTO.setLocationName(itemEntity.getLocation().getName());
			itemDTO.setFoundYn(itemEntity.getFoundYn());
			itemDTO.setPickupDate(itemEntity.getPickupDate());
			itemDTO.setPickupPersonName(itemEntity.getPickupPersonName());
			itemDTO.setDescription(itemEntity.getDescription());
			itemDTO.setUserId(itemEntity.getUser().getId());
			
			List<PictureEntity> pictureEntityList = pictureRepository.findAllByItemId(itemEntity.getId());
			List<PictureDTO> pictureDTOList = new ArrayList<>();
			
			for( PictureEntity pictureEntity : pictureEntityList) { // ???
				PictureDTO pictureDTO = new PictureDTO();
				pictureDTO.setUrl(pictureEntity.getUrl());
				pictureDTOList.add(pictureDTO);
			}
			
			itemDTO.setPictures(pictureDTOList); 
			
			
			itemDTOList.add(itemDTO);
			
		} // end of for
		
		PageResponse<ItemDTO> pageItems = new PageResponse<>();
		pageItems.setList(itemDTOList);
		pageItems.setCurrentPage(page);
		pageItems.setHasNext(page < res.getTotalPages());
		pageItems.setHasPrevious(page > 1);
		pageItems.setTotalElements(res.getTotalElements());
		pageItems.setTotalPages(res.getTotalPages());
		
		return pageItems;

	}
	

	
	
	/*
	//여러 아이템을 가져오는 메소드 --> 아직 foundYn 적용 전 
	public PageResponse<ItemDTO> getItems(int page, int size, String keyword, String categoryId, String type) {
		// type : "all" --> 전체
		// type : "found" --> 찾기완료된것만 조회
		// type : "not-found" --> 찾기미완료된것만 조회
		
		String findPattern = "%" + keyword + "%";// ex "bottle%"
		
		Sort s = Sort.by(Sort.Order.desc("createdAt"));
		PageRequest pageRequest = PageRequest.of(page-1, size, s);
		
		Page<ItemEntity> res = null;
		
		if(categoryId.equals("ALL")) {
			if(keyword == null || keyword.equals("")) {
				// category X, keyword X
				res = itemRepository.findAllBy(pageRequest);
			}else {
				// category X, keyword O
				res = itemRepository.findAllByItemNameLike(findPattern, pageRequest);
			}
		} else {
			
			if(keyword == null || keyword.equals("")) {
				//category O keyword X
				res = itemRepository.findAllByCategoryId(categoryId, pageRequest);
			}else {
				// category O keyword O
				res = itemRepository.findAllByTitleLikeAndCategory(findPattern, categoryId, pageRequest);
			}
		}
		
		// res.getContent() --> 리스트<ItemEntity>  --> 리스트<ItemDTO>
		// ItemEntity를 DTO로 바꿔주고 PageResponse에다 담아서 Controller로 전달
		List<ItemDTO> ItemDTOList = new ArrayList<>();
		
		for(ItemEntity itemEntity : res.getContent()) {
			ItemDTO itemDTO = new ItemDTO();			
			itemDTO.setId(itemEntity.getId());
			itemDTO.setName(itemEntity.getName());
			itemDTO.setCategoryId(itemEntity.getCategoryId());
			itemDTO.setCategoryName(itemEntity.getCategoryName());
			itemDTO.setFoundDate(itemEntity.getFoundDate());
			itemDTO.setNameTag(itemEntity.getNameTag());
			itemDTO.setLocationId(itemEntity.getLocationId());
			itemDTO.setLocationName(itemEntity.getLocationName());
			itemDTO.setFoundYn(itemEntity.getFoundYn());
			itemDTO.setPickupDate(itemEntity.getPickupDate());
			itemDTO.setPickupPersonName(itemEntity.getPickupPersonName());
			itemDTO.setDescription(itemEntity.getDescription());
			itemDTO.setUserId(itemEntity.getUserId());
			
			List<PictureEntity> pictureEntityList = pictureRepository.findAllByItemId(itemEntity.getId());
			List<PictureDTO> pictureDTOList = new ArrayList<>();
			
			for( PictureEntity pictureEntity : pictureEntityList) { // ???
				PictureDTO pictureDTO = new PictureDTO();
				pictureDTO.setUrl(pictureEntity.getUrl());
				pictureDTOList.add(pictureDTO);
			}
			
			itemDTO.setPictures(pictureDTOList); 
			
			
			ItemDTOList.add(itemDTO);
			
		} // end of for
		
		PageResponse<ItemDTO> pageItems = new PageResponse<>();
		pageItems.setList(ItemDTOList);
		pageItems.setCurrentPage(page);
		pageItems.setHasNext(page < res.getTotalPages());
		pageItems.setHasPrevious(page > 1);
		pageItems.setTotalElements(res.getTotalElements());
		pageItems.setTotalPages(res.getTotalPages());
		
		return pageItems;
		
	} // end of getItems
	*/
}	