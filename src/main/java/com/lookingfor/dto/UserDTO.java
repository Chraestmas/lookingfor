package com.lookingfor.dto;

public class UserDTO {
	private String 		id;
	private String 		name;
	private String 		password;
	private String 		permit;
	private String 		superAdmin;
	
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
	public String getSuperAdmin() {
		return superAdmin;
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
	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}
	
}
