package com.lookingfor.dto;

public class CodeDTO {
	private String email;         // 사용자 이메일
	private String code;

    // 기본 생성자와 getter, setter
    public CodeDTO(String code) {
        this.code = code;
    }

    public CodeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CodeDTO(String email, String code) {
		super();
		this.email = email;
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
