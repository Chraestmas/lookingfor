package com.lookingfor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.dto.PictureDTO;
import com.lookingfor.entity.CategoryEntity;
import com.lookingfor.entity.ItemEntity;
import com.lookingfor.entity.LocationEntity;
import com.lookingfor.entity.PictureEntity;
import com.lookingfor.entity.UserEntity;
import com.lookingfor.repository.CategoryRepository;
import com.lookingfor.repository.ItemRepository;
import com.lookingfor.repository.LocationRepository;
import com.lookingfor.repository.PictureRepository;
import com.lookingfor.repository.UserRepository;
import com.lookingfor.response.PageResponse;

@Service
public class ItemService {
	
	ItemRepository itemRepository;
	PictureRepository pictureRepository;
	CategoryRepository categoryRepository;
	LocationRepository locationRepository;
	UserRepository userRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository, PictureRepository pictureRepository, 
			CategoryRepository categoryRepository, LocationRepository locationRepository,
			UserRepository userRepository) {
		this.itemRepository = itemRepository;
		this.pictureRepository = pictureRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
		this.userRepository = userRepository;
	}
	
	//새로운 item을 create 하는 메소드
	public ItemDTO createItem(ItemDTO itemDto) {
		ItemDTO res = new ItemDTO();
		
		// ItemDTO를 ItemEntity로 변환
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setName(itemDto.getName());
		
		Optional<CategoryEntity> optCe = categoryRepository.findById(itemDto.getCategoryId());
		if(!optCe.isPresent()) {
			return res;
		}
		itemEntity.setCategory( optCe.get() );
		
		itemEntity.setFoundDate(itemDto.getFoundDate());
		itemEntity.setNameTag(itemDto.getNameTag());
		
		Optional<LocationEntity> optLe = locationRepository.findById(itemDto.getLocationId());
		if(!optLe.isPresent()) {
			return res;
		}
		itemEntity.setLocation(optLe.get());
		
		itemEntity.setFoundYn('N');
		
		itemEntity.setDescription(itemDto.getDescription());
		
		Optional<UserEntity> optUe = userRepository.findById(itemDto.getUserId());
		if(!optUe.isPresent()) {
			return res;
		}
		itemEntity.setUser(optUe.get());
		
		try {
			ItemEntity savedItem = itemRepository.save(itemEntity);
//			System.out.println(savedItem.getCategory().getName());
			// front에 전달하기 위해 Entity를 DTO로 변환
//			System.out.println(savedItem.getId());
			res.setId(savedItem.getId());
			res.setName(savedItem.getName());
			res.setCategoryId(savedItem.getCategory().getId());
			res.setCategoryName(savedItem.getCategory().getName());
			res.setFoundDate(savedItem.getFoundDate());
			res.setNameTag(savedItem.getNameTag());
			res.setLocationId(savedItem.getLocation().getId());
			res.setLocationName(savedItem.getLocation().getName());
			res.setFoundYn(savedItem.getFoundYn());
			res.setPickupDate(savedItem.getPickupDate());
			res.setPickupPersonName(savedItem.getPickupPersonName());
			res.setDescription(savedItem.getDescription());
			res.setUserId(savedItem.getUser().getId());
			
			return res;

			
		}catch(Exception e) {
			return res;
		}

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