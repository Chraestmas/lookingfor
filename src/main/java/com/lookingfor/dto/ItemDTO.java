package com.lookingfor.dto;

import java.time.LocalDate;
import java.util.List;

public class ItemDTO {
	
	private Integer 	id;
	private String 		name;
	private Integer 	categoryId;
	private String		categoryName;
	private LocalDate 	foundDate;
	private String 		nameTag;
	private Integer 	locationId;
	private String 		locationName;
	private Character	 	foundYn;
	private LocalDate 	pickupDate;
	private String 		pickupPersonName;
	private String 		description;
	private String 		userId;
	private List<PictureDTO> pictures;
	
	@Override
	public String toString() {
		String res = "";
		res += "id:" +id;
		res += "name:" +name;
		res += "categoryId:" +categoryId;
		res += "categoryName:" +categoryName;
		res += "foundDate:" +foundDate;
		res += "nameTag:" +nameTag;
		res += "locationId:" +locationId;
		res += "locationName:" +locationName;
		res += "foundYn:" +foundYn;
		res += "pickupDate:" +pickupDate;
		res += "pickupPersonName:" +pickupPersonName;
		res += "description:" +description;
		res += "userId:" +userId;
		
		return res;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public LocalDate getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(LocalDate foundDate) {
		this.foundDate = foundDate;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Character getFoundYn() {
		return foundYn;
	}

	public void setFoundYn(Character foundYn) {
		this.foundYn = foundYn;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getPickupPersonName() {
		return pickupPersonName;
	}

	public void setPickupPersonName(String pickupPersonName) {
		this.pickupPersonName = pickupPersonName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<PictureDTO> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureDTO> pictures) {
		this.pictures = pictures;
	}
	
}
