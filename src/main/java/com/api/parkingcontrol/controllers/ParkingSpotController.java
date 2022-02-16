package com.api.parkingcontrol.controllers;


import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class ParkingSpotController {
	
	@Autowired
	ParkingSpotService parkingSpotService;

		
	@PostMapping("/pot")
	public ResponseEntity<Object>saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
			
		if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Licence Plate Car is already in use ");
		}
		
		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use ");
		}
		
		if (parkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Apartament and Block is already in use ");
		}
			var parkingSpotModel = new ParkingSpotModel();
			BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
			parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(parkingSpotService.save(parkingSpotModel));
			
}


}
