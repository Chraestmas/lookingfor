package com.lookingfor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
	@Id
	private String id;			
	private String name;		
	private String password;
	private String permit;
	@Column(name="superadmin")
	private String superAdmin;
	
	//getters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
