package com.lookingfor.controller;

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
	
	// id로 모임을 조회하는 api
	@GetMapping("/api/item/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") Integer id) {
		return ResponseEntity.status(200).body(is.getItemById(id));
	}
}
