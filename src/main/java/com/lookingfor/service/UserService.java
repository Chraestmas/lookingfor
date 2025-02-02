package com.lookingfor.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.lookingfor.dto.UserDTO;
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
    		throw new RuntimeException("Invalid credentials");
    	}
    	
    	UserEntity loginUser = optUser.get();
    	
    	// 허용되는 아이디인지 확인 (permit)
    	if(loginUser.getPermit().equals("N")) {
    		throw new RuntimeException("Not permitted user");
    	}
    	
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
            throw new RuntimeException("Invalid Id or Password");
        }
    }
	
	//user 생성하는 메소드 
	public UserDTO createUser(UserDTO userDTO) {
		
		//id 중복 확인 
		Optional<UserEntity> optionalUser = userRepository.findById(userDTO.getId());
		if(optionalUser.isPresent()) {
			throw new RuntimeException("Already Existed Id");
		}
		
		// UserDTO를 UserEntity로 변환
		UserEntity userEntity = new UserEntity();
		
		userEntity.setId(userDTO.getId());
		userEntity.setName(userDTO.getName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setPermit("N");
		
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
		userDTO.setSuperAdmin(userEntity.getSuperAdmin());
		
		return userDTO;
	} // end of getUserById
	
	
	///////////////////////////////////////////////////////////////////////////////////

	//모든 user를 조회하는 메소드 
	public List<UserDTO> getUsers() {
		
		List<UserEntity> res = null;
		
		res = userRepository.findAll();
		
		// res.getContent() --> 리스트<UserEntity>  --> 리스트<UserDTO>
		// UserEntity를 DTO로 바꿔주고 userDTOList에 담아서 Controller로 전달
		List<UserDTO> userDTOList = new ArrayList<>();
		
		for(UserEntity userEntity : res) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(userEntity.getId());
			userDTO.setName(userEntity.getName());
			userDTO.setPermit(userEntity.getPermit());
			userDTO.setSuperAdmin(userEntity.getSuperAdmin());

			userDTOList.add(userDTO);			
		} // end of for
		
		return userDTOList;

	} // end of getUsers
	
	
	
	
	//특정 user permit 수정하는 메소드 
	public UserDTO updateUserPermitById(String id, String permit) {
		
		UserDTO responseUser = new UserDTO();
		
		Optional<UserEntity> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			return responseUser; // 해당 id의 user이 없음
		}
		
		
		// UserDTO를 UserEntity로 변환
		UserEntity userEntity = optionalUser.get();
		userEntity.setPermit(permit);
		
		try {
			UserEntity savedUser = userRepository.save(userEntity);
			
			// front에 전달하기 위해 Entity를 DTO로 변환
			responseUser.setId(savedUser.getId());
			responseUser.setName(savedUser.getName());
			responseUser.setPermit(savedUser.getPermit());

			return responseUser;

		}catch(Exception e) {
			return responseUser;
		}
	
	} //end of updateUserById
	
	
	//user 삭제하는 메소드
	public boolean deleteUserById(String id) {
		// 1. 삭제하려는 대상이 db에 있는지 확인
		Optional<UserEntity> optUser = userRepository.findById(id);
		if(!optUser.isPresent()) {
			return false;
		}
		
		userRepository.deleteById(id);
		
		return true;
	} // end of deleteUserById
	
}
