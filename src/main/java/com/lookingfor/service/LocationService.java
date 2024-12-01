package com.lookingfor.service;

import java.util.ArrayList;
import java.util.List;

import com.lookingfor.dto.LocationDTO;
import com.lookingfor.entity.LocationEntity;
import com.lookingfor.repository.LocationRepository;

public class LocationService {
	
	LocationRepository locationRepository;
	
	
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}


	//모든 location 값을 가져오는 메소드 
	public List<LocationDTO> getLocations() {
		
		List<LocationEntity> res = null;
		LocationDTO locationDTO = new LocationDTO();
		
		res = locationRepository.findAll();
		
		List<LocationDTO> locationDTOList = new ArrayList<>();
		
		for(LocationEntity locationEntity : res) {
			locationDTO.setId(locationEntity.getId());
			locationDTO.setName(locationEntity.getName());
			locationDTOList.add(locationDTO);
		}//end of for
	
		return locationDTOList;
	}

}