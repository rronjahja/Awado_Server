package com.awado.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	// This class is required for creating a response containing the JWT to be returned to the user.
	
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}