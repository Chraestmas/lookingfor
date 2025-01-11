package com.lookingfor.dto;

public class UserDTO {
	private String 		id;
	private String 		name;
	private String 		password;
	private String 		permit;
	
	//getters
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPermit() {
		return permit;
	}
	
	//setters
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	
}
