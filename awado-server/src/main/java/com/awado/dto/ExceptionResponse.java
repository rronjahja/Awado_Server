package com.awado.dto;

public class ExceptionResponse {

	private String message;
	private String error;

	public ExceptionResponse(String errorMessage, String error) {
		super();
		this.message = errorMessage;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

}
