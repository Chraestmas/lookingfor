package com.lookingfor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PictureEntity {
	@Id
	private Integer 	id;
	private String 		url;
	private Integer 	itemId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
}
