package com.lookingfor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.lookingfor.dto.LocationDTO;
import com.lookingfor.service.LocationService;

@Controller
@CrossOrigin(origins = "http://localhost:8089")
public class LocationController {
	
	private LocationService locationService;
	
	
	public LocationController(LocationService locationService){
		this.locationService = locationService;
	}
	
	//모든 location를 가져오는 api
	@GetMapping("/api/location")
	public ResponseEntity<List<LocationDTO>> getLocations(){

		return ResponseEntity.status(200).body(locationService.getLocations());
	}

}
