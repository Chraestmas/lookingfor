package com.lookingfor.dto;

public class EmailDTO {
	private String email;

	public EmailDTO() {
		// TODO Auto-generated constructor stub
	}
    // 기본 생성자와 getter, setter
    public EmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
