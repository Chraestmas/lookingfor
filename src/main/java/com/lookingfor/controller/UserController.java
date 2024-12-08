package com.lookingfor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

	@PostMapping("/api/user/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody UserDTO resetPasswordDto) {
		boolean passwordUpdated = userService.resetPassword(resetPasswordDto);

		if (passwordUpdated) {
			return ResponseEntity.ok("Password reset successfully");
		} else {
			return ResponseEntity.status(500).body("Failed to reset password");
		}
	}

	@PostMapping("/api/login")
	public ResponseEntity<String> loginUser(@RequestBody UserDTO userDto) {
		try {
			String jwtToken = userService.loginUser(userDto); // 로그인 후 JWT 토큰 반환
			return ResponseEntity.ok().body(jwtToken); // JWT 토큰을 응답
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
