package com.lookingfor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.response.PageResponse;
import com.lookingfor.service.ItemService;

@Controller
@CrossOrigin(origins = "http://localhost:8001")

public class ItemController {
	
	ItemService itemService;
	
	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	// id로 item을 조회하는 api (한 건) 
	@GetMapping("/api/item/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") Integer id) {
		return ResponseEntity.status(200).body(itemService.getItemById(id));
	}
	
	//조건에 해당하는 item을 조회하는 api (여러 건 가능)
	@GetMapping("/api/item")
	public ResponseEntity<PageResponse<ItemDTO>> getItems(
		@RequestParam(name = "categoryId" , required = false) List<Integer> categoryId,
		@RequestParam(name = "foundYn", required = false) List<Character> foundYn,
		@RequestParam(name = "itemName", required = false) String itemName,
		@RequestParam(name = "page", defaultValue = "1") Integer page,
		@RequestParam(name="size", defaultValue="3") Integer size) {
		
		return ResponseEntity.status(200).body(itemService.getItems(page, size, foundYn, categoryId, itemName));
	}
	
	//item 정보를 입력하는 api
	@PostMapping("/api/item")
	public ResponseEntity<ItemDTO> createNewItem(@RequestBody ItemDTO itemDto) {
		return ResponseEntity.status(200).body(itemService.createItem(itemDto));

	}
	
	//item 정보를 수정하는 api
	@PostMapping("/api/item/{id}")
	public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDto) {
		return ResponseEntity.status(200).body(itemService.createItem(itemDto));
	}
	
}








