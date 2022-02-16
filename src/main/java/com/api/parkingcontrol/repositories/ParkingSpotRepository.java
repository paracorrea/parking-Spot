package com.api.parkingcontrol.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.parkingcontrol.models.ParkingSpotModel;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{
	
		// declaração de um método - Contrução em Service e utilização no model
		boolean existsByLicensePlateCar(String licensePlateCar);
		boolean existsByParkingSpotNumber(String parkingSpotNumber);
		boolean existsByApartamentAndBlock(String apartament, String block);
}
