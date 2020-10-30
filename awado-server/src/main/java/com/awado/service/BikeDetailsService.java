package com.awado.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.awado.common.ServiceAccess;

import com.awado.dto.BikeDTO;
import com.awado.exception.ServiceException;

import com.awado.model.BikeModel;
import com.awado.repository.BikeRepository;

@Component
public class BikeDetailsService {

	@Autowired
	BikeRepository bikeRepository;

	private static final String DAIMLER_API_URL = "https://cvl.daimler-tss.com/icity/vss/bikes/";

	private ServiceAccess createServiceAccess() {
		String username = "HftTecUser";
		String password = "rG9F6V11HsAdmYchmr9Wg9nMsyJdVW";
		return new ServiceAccess(username, password, true);
	}

	public BikeDTO getBikeDetails(String bikeId) {
		String request = DAIMLER_API_URL + bikeId;
		ServiceAccess serviceAccess = createServiceAccess();
		JSONObject jsonObject = serviceAccess.requestObject(request);
		BikeDTO bike = new BikeDTO();
		if (jsonObject != null) {
			bike.setBikeId(bikeId);
			bike.setBikeName("test");
			bike.setLatitude(jsonObject.getJSONObject("geo").getDouble("latitude"));
			bike.setLongitude(jsonObject.getJSONObject("geo").getDouble("longitude"));
			bike.setAvailability(true);
			bike.setBatteryLevel(jsonObject.getInt("fuelLevel"));
			return bike;
		} else {
			throw new ServiceException("Unable to fetch Bike Details");
		}
	}

	public void createBike(BikeDTO bikeDto) {
		try {
			BikeModel bikeModel = new BikeModel();
			bikeModel.setBikeName(bikeDto.getBikeName());
			bikeModel.setBikeId(bikeDto.getBikeId());
			bikeModel.setAvailability(true);
			bikeRepository.save(bikeModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("Failed to register the Bike");
		}
	}

	public List<BikeDTO> getAllBikes() {
		List<BikeDTO> bikeList = new ArrayList<BikeDTO>();
		String request = DAIMLER_API_URL;
		ServiceAccess serviceAccess = createServiceAccess();
		JSONArray jsonArray = serviceAccess.requestArray(request);
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				BikeDTO bike = new BikeDTO();
				bike.setBikeId(jsonObj.getString("vin"));
				bike.setBikeName("test" + i);
				if (jsonObj.getJSONObject("geo").has("latitude")) {
					bike.setLatitude(jsonObj.getJSONObject("geo").getDouble("latitude"));
				}
				if (jsonObj.getJSONObject("geo").has("latitude")) {
					bike.setLongitude(jsonObj.getJSONObject("geo").getDouble("longitude"));
				}
				if (jsonObj.getJSONObject("connection").has("connected")) {
					bike.setAvailability(jsonObj.getJSONObject("connection").getBoolean("connected"));
				}
				if (jsonObj.has("fuelLevel")) {
					bike.setBatteryLevel(jsonObj.getInt("fuelLevel"));
				}
				bikeList.add(bike);
			}
		}
		return bikeList;
	}
}
