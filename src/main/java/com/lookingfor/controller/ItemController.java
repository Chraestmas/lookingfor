package com.lookingfor.controller;

<<<<<<< HEAD
public class ItemController {

}
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.service.ItemService;

@Controller
@CrossOrigin(origins = "http://localhost:8001")

public class ItemController {
	
	ItemService is;
	
	@Autowired
	public ItemController(ItemService is) {
		this.is = is;
	}
	
	// item 리스트를 조회하는 api
//	@GetMapping("/api/items")
//	public ResponseEntity<PageResponse<ItemDTO>> getItems(
//			@RequestParam(name = "page", defaultValue = "1") int page,
//			@RequestParam(name="size", defaultValue = "8") int size,
//			@RequestParam(name="keyword", required = false) String keyword,
//			@RequestParam(name="category", defaultValue = "ALL") String category) {
//		// ItemsDTO가 리스트요소로 들어있는 PageResponse가
//		// body에 들어있는 ResponseEntity를 front에 전달
//		
//		
//		return ResponseEntity.status(200).body(is.getItems(page, size, keyword, category));
//	}
	
	// id로 모임을 조회하는 api
	@GetMapping("/api/item/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") Integer id) {
		return ResponseEntity.status(200).body(is.getItemById(id));
	}
}













>>>>>>> de9ff16db3ed17128dc5dabd9c3538f938296a51
