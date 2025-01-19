package com.lookingfor.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lookingfor.dto.ItemDTO;
import com.lookingfor.dto.UserDTO;
import com.lookingfor.response.PageResponse;
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
		
		try {
			return ResponseEntity.status(200).body(userService.createUser(userDto));
		} catch (Exception e) {
			System.out.println("[ErrMsg] UserController>createNewUser : " + e.getMessage());
			if(e.getMessage().equals("Already Existed Id")) { // 이미 존재하는 id로 계정을 생성하는 경
				return ResponseEntity.status(401).body(null);
			}else {
				return ResponseEntity.status(500).body(null);
			}
		}
	}


	@PostMapping("/api/login")
	public ResponseEntity<HashMap<String, String>> loginUser(@RequestBody UserDTO userDto) {
		
		try {
			return ResponseEntity.ok().body(userService.loginUser(userDto)); // JWT 토큰을 응답
		} catch (Exception e) {
			if(e.getMessage().equals("Not permitted user")) { // permit이 N인 경우
				return ResponseEntity.status(403).body(null);
			}else if(e.getMessage().equals("Invalid Id or Password")) { // 비밀번호를 틀렸을경우
				return ResponseEntity.status(400).body(null);
			}else if(e.getMessage().equals("Invalid credentials")) { // id가 존재하지 않는 경우
				return ResponseEntity.status(401).body(null);
			}else {
				return ResponseEntity.status(500).body(null);
			}
			
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
	
	///////////////////////////////////////////////////////////////////////////////////
	
	//모든 user를 조회하는 메소드  api (여러 건)
	@GetMapping("/api/users")
	public ResponseEntity<List<UserDTO>> getUsers() {
		return ResponseEntity.status(200).body(userService.getUsers());
	}
	
	//user permit 정보를 수정하는 api
	@PutMapping("/api/user/{id}")
	public ResponseEntity<UserDTO> updateUserPermit(@PathVariable(name = "id") String id, @RequestParam("permit") String permit) throws JsonMappingException, JsonProcessingException {
		
		return ResponseEntity.status(200).body(userService.updateUserPermitById(id, permit));
	}
	
	@DeleteMapping("/api/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") String id){
		
		if(userService.deleteUserById(id)) {
			return ResponseEntity.status(200).body("성공");	
		}
		return ResponseEntity.status(400).body("실패");	
		
	}
}
