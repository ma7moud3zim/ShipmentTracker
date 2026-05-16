package com.azim.shipmenttracker.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shipments") 

public class ShipmentController {
    @Autowired
	private ShipmentService shipServ;

    public ShipmentController(ShipmentService shipServ) {
        this.shipServ = shipServ;
    }
    
	@PostMapping
	public ResponseEntity<?> createShipment(@Valid @RequestBody 
													ShipmentDTO.CreateShipmentRequest request) {
		var shipment = shipServ.createShipment(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllShipments() {
		var shipments = shipServ.getAllShipments();
		return ResponseEntity.status(HttpStatus.OK).body(shipments);
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>  getShipmentById(@PathVariable Long id) {
		ShipmentDTO.ShipmentResponse shipment = shipServ.getShipmentById(id);
		return ResponseEntity.status(HttpStatus.OK).body(shipment);
	
	}
	
	@GetMapping("/track/{trackingNumber}")
	public ResponseEntity<?>  getShipmentByTrackNum(@PathVariable String trackingNumber) {
		ShipmentDTO.ShipmentResponse shipment = shipServ.getShipmentByTrackingNumber(trackingNumber);
		return ResponseEntity.status(HttpStatus.OK).body(shipment);
	
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<?>  updateShipmentStatus(@PathVariable Long id,@Valid @RequestBody ShipmentDTO.UpdateStatusRequest request) {
		ShipmentDTO.ShipmentResponse shipment = shipServ.updateShipmentStatus(request,id);
		return ResponseEntity.status(HttpStatus.OK).body(shipment);
	}
}
