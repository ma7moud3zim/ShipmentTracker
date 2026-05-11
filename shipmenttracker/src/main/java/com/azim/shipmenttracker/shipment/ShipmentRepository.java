package com.azim.shipmenttracker.shipment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
	
	Optional<Shipment> findByTrackingNumber (String trackNum);
	
}
