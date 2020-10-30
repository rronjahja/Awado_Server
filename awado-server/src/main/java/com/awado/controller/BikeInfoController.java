package com.awado.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.awado.dto.BikeDTO;
import com.awado.dto.Response;
import com.awado.exception.ServiceException;
import com.awado.service.BikeDetailsService;

@RestController
public class BikeInfoController {

	@Autowired
	BikeDetailsService bikeDetailsService;

	@GetMapping(value = "/bikes/{bikeId}", headers = "Accept=application/json")
	public ResponseEntity<Response> getBike(@PathVariable("bikeId") String bikeId) throws Exception {
		if (StringUtils.isNotBlank(bikeId)) {
			BikeDTO bike = bikeDetailsService.getBikeDetails(bikeId);
			Response response = new Response();
			response.setData(bike);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			throw new ServiceException("Invalid Bike Id");
		}
	}

//	@GetMapping(value = "/bikes", headers = "Accept=application/json")
//	public ResponseEntity<Response> getAllBikes() throws Exception {
//		List<BikeDTO> bikeList = bikeDetailsService.getAllBikes();
//		if(!bikeList.isEmpty()) {
//			Response response = new Response();
//			response.setData(bikeList);
//			return new ResponseEntity<Response>(response, HttpStatus.OK);
//		} else {
//			throw new ServiceException("Unable to fetch the Bikes");
//		}
//	}
}
