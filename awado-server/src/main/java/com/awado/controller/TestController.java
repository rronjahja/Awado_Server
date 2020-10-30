package com.awado.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/")
	public ResponseEntity<String> home() {
		ResponseEntity<String> response = new ResponseEntity<String>("Spring Boot", HttpStatus.OK); 
		return response;
		
	}
}
