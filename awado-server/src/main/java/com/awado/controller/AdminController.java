package com.awado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awado.dto.BikeDTO;
import com.awado.dto.Response;
import com.awado.exception.ServiceException;
import com.awado.service.BikeDetailsService;

@RestController
public class AdminController {

	@Autowired
	BikeDetailsService bikeDetailsService;

	@GetMapping(value = "/admin/bikes", headers = "Accept=application/json")
	public ResponseEntity<Response> getAllBikes() throws Exception {
		Response response = new Response();
		List<BikeDTO> bikeList = bikeDetailsService.getAllBikes();
		if (!bikeList.isEmpty() && bikeList != null) {
			response.setData(bikeList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new ServiceException("Unable to fetch the Bikes");
		}
	}

	@PostMapping(value = "/bikes", headers = "Accept=application/json")
	public ResponseEntity<Response> addBike(@RequestBody BikeDTO bikeDto) {
		bikeDetailsService.createBike(bikeDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
