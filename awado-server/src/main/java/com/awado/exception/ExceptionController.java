package com.awado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.awado.dto.ExceptionResponse;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<ExceptionResponse> serviceException(ServiceException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
