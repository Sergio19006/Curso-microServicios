package com.example.security.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("serial")
@JsonInclude(value = Include.NON_EMPTY)
public class AuthToken implements Serializable {
	private boolean success = false;
    private String token;
    private String name;
    
    public AuthToken() { }
	public AuthToken(boolean success, String token, String name) {
		this.success = success;
		this.token = token;
		this.name = name;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public String getToken() {
		return token;
	}
	public String getName() {
		return name;
	}
    
}
