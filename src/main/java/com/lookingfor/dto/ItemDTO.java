package com.lookingfor.dto;

public class ItemDTO {
	
	private Integer id;
	private String name;
	private Integer category_id;
	private String found_date;
	private String name_tag;
	private Integer location_id;
	private Boolean found_yn;
	private String pickup_date;
	private String pickup_person_name;
	private String description;
	private String user_id;
	
	
	
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
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getFound_date() {
		return found_date;
	}
	public void setFound_date(String found_date) {
		this.found_date = found_date;
	}
	public String getName_tag() {
		return name_tag;
	}
	public void setName_tag(String name_tag) {
		this.name_tag = name_tag;
	}
	public Integer getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}
	public Boolean getFound_yn() {
		return found_yn;
	}
	public void setFound_yn(Boolean found_yn) {
		this.found_yn = found_yn;
	}
	public String getPickup_date() {
		return pickup_date;
	}
	public void setPickup_date(String pickup_date) {
		this.pickup_date = pickup_date;
	}
	public String getPickup_person_name() {
		return pickup_person_name;
	}
	public void setPickup_person_name(String pickup_person_name) {
		this.pickup_person_name = pickup_person_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
