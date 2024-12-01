package com.lookingfor.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	//item을 삭제하는 메소드
	public boolean deleteItemById(Integer id) {
		// 1. 삭제하려는 대상이 db에 있는지 확인
		Optional<ItemEntity> optItem = itemRepository.findById(id);
		if(!optItem.isPresent()) {
			return false;
		}
		
		itemRepository.deleteById(id);
		
		return true;
		
	}
	
	
	//사진 이미지를 저장하는 메소드
	private String saveImageFile(MultipartFile imageFile) {
		String path =  System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
		String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
		
		// 실제로 받아온 MultipartFile 타입을 기반으로 저장시킬 새로운 File타입을 생성
		File file = new File(path, fileName);
		
		try {
			imageFile.transferTo(file);
			return "/uploads/" + file.getName();
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("파일 전송 중 오류 발생");
			return null;
		}
		
		
		
	}
	
	
	//새로운 item을 create 하는 메소드
	public ItemDTO createItem(ItemDTO itemDto) {
		ItemDTO itemDTO = new ItemDTO();
		List<PictureEntity> pictures = new ArrayList<>();
		// ItemDTO를 ItemEntity로 변환
		ItemEntity itemEntity = new ItemEntity();
		
		// 사진 파일 저장
		if(itemDto.getPhotos() != null) {
		
			for(MultipartFile photo : itemDto.getPhotos()) {
				
				if(photo != null && !photo.isEmpty()) {
					String imagePath = saveImageFile(photo);
					System.out.println(imagePath);
					PictureEntity pe = new PictureEntity();
					pe.setUrl(imagePath);
					pe.setItem(itemEntity);
					pictures.add(pe);
				}
				
			}
		}
		
		
		itemEntity.setName(itemDto.getName());
		System.out.println("category id" + itemDto.getCategoryId());
		Optional<CategoryEntity> optCe = categoryRepository.findById(itemDto.getCategoryId());
		if(!optCe.isPresent()) {
			System.out.println("확인1");
			return itemDTO;
		}
		
		
		itemEntity.setCategory( optCe.get() );
		
		itemEntity.setFoundDate(itemDto.getFoundDate());
		itemEntity.setNameTag(itemDto.getNameTag());
		System.out.println("category id" + itemDto.getLocationId());
		
		Optional<LocationEntity> optLe = locationRepository.findById(itemDto.getLocationId());
		if(!optLe.isPresent()) {
			System.out.println("확인2");
			return itemDTO;
		}
		itemEntity.setLocation(optLe.get());
		
		itemEntity.setFoundYn('N');
		
		itemEntity.setDescription(itemDto.getDescription());
		
		Optional<UserEntity> optUe = userRepository.findById(itemDto.getUserId());
		if(!optUe.isPresent()) {
			System.out.println("확인3");
			return itemDTO;
		}
		itemEntity.setUser(optUe.get());
		
		itemEntity.setPictures(pictures);
		
		try {
			ItemEntity savedItem = itemRepository.save(itemEntity);
//			System.out.println(savedItem.getCategory().getName());
			// front에 전달하기 위해 Entity를 DTO로 변환
//			System.out.println(savedItem.getId());
			itemDTO.setId(savedItem.getId());
			itemDTO.setName(savedItem.getName());
			itemDTO.setCategoryId(savedItem.getCategory().getId());
			itemDTO.setCategoryName(savedItem.getCategory().getName());
			itemDTO.setFoundDate(savedItem.getFoundDate());
			itemDTO.setNameTag(savedItem.getNameTag());
			itemDTO.setLocationId(savedItem.getLocation().getId());
			itemDTO.setLocationName(savedItem.getLocation().getName());
			itemDTO.setFoundYn(savedItem.getFoundYn());
			itemDTO.setPickupDate(savedItem.getPickupDate());
			itemDTO.setPickupPersonName(savedItem.getPickupPersonName());
			itemDTO.setDescription(savedItem.getDescription());
			itemDTO.setUserId(savedItem.getUser().getId());
			
			
			return itemDTO;

		}catch(Exception e) {
			System.out.println("확인4");
			e.printStackTrace();
			return itemDTO;
		}

	}
	
	//특정 item을 수정하는 메소드 
	public ItemDTO updateItemById(Integer id, ItemDTO itemDto) {
		
		ItemDTO responseItem = new ItemDTO();
		
		Optional<ItemEntity> optionalItem = itemRepository.findById(id);
		if(!optionalItem.isPresent()) {
			return responseItem; // 해당 id의 item이 없음
		}
		
		
		// ItemDTO를 ItemEntity로 변환
		ItemEntity itemEntity = optionalItem.get();
		if(itemDto.getName() != null) {
			itemEntity.setName(itemDto.getName());			
		}
		
		if(itemDto.getCategoryId() != null) {
			Optional<CategoryEntity> optCe = categoryRepository.findById(itemDto.getCategoryId());
			if(!optCe.isPresent()) {
				return responseItem;
			}
			itemEntity.setCategory( optCe.get() );			
		}

		if(itemDto.getFoundDate() != null) {
			itemEntity.setFoundDate(itemDto.getFoundDate());			
		}
		if(itemDto.getNameTag() != null) {
			itemEntity.setNameTag(itemDto.getNameTag());			
		}
		if(itemDto.getLocationId() != null) {
			Optional<LocationEntity> optLe = locationRepository.findById(itemDto.getLocationId());
			if(!optLe.isPresent()) {
				return responseItem;
			}
			itemEntity.setLocation(optLe.get());			
		}
		
		if(itemDto.getFoundYn() != null) {
			itemEntity.setFoundYn(itemDto.getFoundYn());			
		}
		
		if(itemDto.getDescription() != null) {
			itemEntity.setDescription(itemDto.getDescription());			
		}
		
		if(itemDto.getUserId() != null) {
			Optional<UserEntity> optUe = userRepository.findById(itemDto.getUserId());
			if(!optUe.isPresent()) {
				return responseItem;
			}
			itemEntity.setUser(optUe.get());			
		}
		
		//추가 
		if(itemDto.getPickupDate() != null) {
			itemEntity.setPickupDate(itemDto.getPickupDate());			
		}
		if(itemDto.getPickupPersonName() != null) {
			itemEntity.setPickupPersonName(itemDto.getPickupPersonName());			
		}
	
		try {
			ItemEntity savedItem = itemRepository.save(itemEntity);
			// front에 전달하기 위해 Entity를 DTO로 변환
			responseItem.setId(savedItem.getId());
			responseItem.setName(savedItem.getName());
			responseItem.setCategoryId(savedItem.getCategory().getId());
			responseItem.setCategoryName(savedItem.getCategory().getName());
			responseItem.setFoundDate(savedItem.getFoundDate());
			responseItem.setNameTag(savedItem.getNameTag());
			responseItem.setLocationId(savedItem.getLocation().getId());
			responseItem.setLocationName(savedItem.getLocation().getName());
			responseItem.setFoundYn(savedItem.getFoundYn());
			responseItem.setPickupDate(savedItem.getPickupDate());
			responseItem.setPickupPersonName(savedItem.getPickupPersonName());
			responseItem.setDescription(savedItem.getDescription());
			responseItem.setUserId(savedItem.getUser().getId());
		
			return responseItem;

		}catch(Exception e) {
			return responseItem;
		}
	
	}
	
	//item을 id로 찾는 메소드 
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
	
	
	//조건값에 해당하는 아이템을 찾는 메소드 
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

}	