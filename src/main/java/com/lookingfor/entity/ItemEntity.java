package com.lookingfor.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ItemEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer 	id;
	private String 		name;
	private Integer 	categoryId;
	private String 		categoryName;
	private LocalDate 	foundDate;
	private String 		nameTag;
	private Integer 	locationId;
	private String 		locationName;
	private boolean 	foundYn;
	private LocalDate 	pickupDate;
	private String 		pickupPersonName;
	private String 		description;
	private String 		userId;
	
	
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
	public boolean getFoundYn() {
		return foundYn;
	}
	public void setFoundYn(boolean foundYn) {
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
	

	
}



