package com.lookingfor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lookingfor.dto.UserDTO;
import com.lookingfor.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:8001")

public class UserController {
	
	UserService userService;
	
	@PostMapping("/api/user")
	public ResponseEntity<UserDTO> createNewUser(@ModelAttribute UserDTO userDto) {
		return ResponseEntity.status(200).body(userService.createUser(userDto));
	}
	
}
