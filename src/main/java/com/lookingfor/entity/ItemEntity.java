package com.lookingfor.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "item")
public class ItemEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer 	id;
	private String 		name;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	private LocalDate 	foundDate;
	private String 		nameTag;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private LocationEntity 	location;
	
	private Character 		foundYn;
	private LocalDate 	pickupDate;
	private String 		pickupPersonName;
	private String 		description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity 		user;
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<PictureEntity> pictures;
	
	
	public List<PictureEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<PictureEntity> pictures) {
		this.pictures = pictures;
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
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	public LocationEntity getLocation() {
		return location;
	}
	public void setLocation(LocationEntity location) {
		this.location = location;
	}
	

	
}



