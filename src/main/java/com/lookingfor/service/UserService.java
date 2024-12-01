package com.lookingfor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.lookingfor.dto.UserDTO;
import com.lookingfor.entity.ItemEntity;
import com.lookingfor.entity.UserEntity;
import com.lookingfor.repository.CategoryRepository;
import com.lookingfor.repository.ItemRepository;
import com.lookingfor.repository.LocationRepository;
import com.lookingfor.repository.PictureRepository;
import com.lookingfor.repository.UserRepository;

public class UserService {
	
	UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDTO createUser(UserDTO userDTO) {
		
		//UserDTO userDTO = new UserDTO();
		// UserDTO를 UserEntity로 변환
		UserEntity userEntity = new UserEntity();
		
		
		userEntity.setId(userDTO.getId());
		userEntity.setName(userDTO.getName());
		userEntity.setPassword(userDTO.getPassword());
		
		try {
			UserEntity savedUser = userRepository.save(userEntity);
			userDTO.setId(savedUser.getId());
			userDTO.setName(savedUser.getName());
			userDTO.setPassword(savedUser.getPassword());
			
			
			return userDTO;

		}catch(Exception e) {
			System.out.println("확인4");
			e.printStackTrace();
			return userDTO;
		}
		
	}
}
