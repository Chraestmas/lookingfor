package com.lookingfor.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.dto.UserDTO;
import com.lookingfor.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:8089")
public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/user")
	public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDto) {
		return ResponseEntity.status(200).body(userService.createUser(userDto));
	}


	@PostMapping("/api/login")
	public ResponseEntity<HashMap<String, String>> loginUser(@RequestBody UserDTO userDto) {
		
		try {
			return ResponseEntity.ok().body(userService.loginUser(userDto)); // JWT 토큰을 응답
		} catch (Exception e) {
			return ResponseEntity.status(401).body(null);
		}
	}
	
	@PostMapping("/api/user/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody UserDTO resetPasswordDto) {
        boolean passwordUpdated = userService.resetPassword(resetPasswordDto);

        if (passwordUpdated) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to reset password");
        }
    }
	
	// id로 user를 조회하는 api (한 건) 
	@GetMapping("/api/user/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
		return ResponseEntity.status(200).body(userService.getUserById(id));
	}
}
