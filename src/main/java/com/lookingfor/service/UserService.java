package com.lookingfor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.dto.PictureDTO;
import com.lookingfor.dto.UserDTO;
import com.lookingfor.entity.ItemEntity;
import com.lookingfor.entity.PictureEntity;
import com.lookingfor.entity.UserEntity;
import com.lookingfor.repository.CategoryRepository;
import com.lookingfor.repository.ItemRepository;
import com.lookingfor.repository.LocationRepository;
import com.lookingfor.repository.PictureRepository;
import com.lookingfor.repository.UserRepository;
import com.lookingfor.util.JwtUtil;

@Service
public class UserService {
	
	UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	// 로그인 처리 및 JWT 토큰 반환
    public HashMap<String, String> loginUser(UserDTO userDto) {
    	Optional<UserEntity> optUser = userRepository.findById(userDto.getId());
    	if(optUser.isEmpty()) {
    		System.out.println("확인1");
    		throw new RuntimeException("Invalid credentials");
    	}
    	
    	UserEntity loginUser = optUser.get();
    	
    	//id, password 검증 
    	if (loginUser.getId().equals(userDto.getId()) && loginUser.getPassword().equals(userDto.getPassword())) {
            // 로그인 성공 시 JWT 토큰 생성
        	System.out.println("확인3");
        	HashMap<String, String> response = new HashMap<>();
        	System.out.println("jwt token : " + JwtUtil.generateToken(userDto.getId()));
        	System.out.println("response : " + response);
        	response.put("authToken", JwtUtil.generateToken(userDto.getId()));
        	System.out.println("response : " + response);
        	response.put("id", userDto.getId());
            return response;
        } else {
        	System.out.println("확인4");
            throw new RuntimeException("Invalid Id or Password");
        }
    }
	
	//user 생성하는 메소드 
	public UserDTO createUser(UserDTO userDTO) {
		
		//UserDTO userDTO = new UserDTO();
		// UserDTO를 UserEntity로 변환
		UserEntity userEntity = new UserEntity();
		
		userEntity.setId(userDTO.getId());
		userEntity.setName(userDTO.getName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setPermit(userDTO.getPermit());
		
		try {
			UserEntity savedUser = userRepository.save(userEntity);
			userDTO.setId(savedUser.getId());
			userDTO.setName(savedUser.getName());
			userDTO.setPassword(savedUser.getPassword());
			userDTO.setPermit(savedUser.getPermit());
			
			return userDTO;

		}catch(Exception e) {
			System.out.println("확인4");
			e.printStackTrace();
//			return userDTO;
			return null;
		}
		
	} // end of createUser
	
	//password 리셋하는 메소드 
	public boolean resetPassword(UserDTO userDto) {
		
        Optional<UserEntity> user = userRepository.findById(userDto.getId());
        if (user.isPresent()) {
            UserEntity u = user.get();
            u.setPassword(userDto.getPassword());  // 새 비밀번호 설정
            userRepository.save(u);      // 비밀번호 저장
            return true;
        }
        return false;
	} // end of resetPassword
	
	
	//user를 id로 찾는 메소드 
	public UserDTO getUserById(String id) {
		UserDTO userDTO = new UserDTO();
		
		Optional<UserEntity> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			return userDTO; // 해당 id의 user 없음
		}
		
		UserEntity userEntity = optionalUser.get();
		
		userDTO.setId(userEntity.getId());
		userDTO.setName(userEntity.getName());
		
		return userDTO;
	} // end of getUserById
	
}
