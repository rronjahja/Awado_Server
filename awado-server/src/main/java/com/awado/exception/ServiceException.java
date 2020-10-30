package com.awado.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4140553621602301386L;

	public ServiceException(String message) {
		super(message);
	}
}