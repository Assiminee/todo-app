package com.toDoList.Response;

public class AuthResponse {

	
	private String jwt;
	
	private String message;
	
	private boolean status;

	public AuthResponse() {
		super();
	}

	public AuthResponse(String jwt, String message, boolean status) {
		super();
		this.jwt = jwt;
		this.message = message;
		this.status = status;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
