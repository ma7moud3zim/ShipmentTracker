package com.azim.shipmenttracker.shipment;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.azim.shipmenttracker.shipment.ShipmentDTO.ShipmentResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j

public class ShipmentService {

	private final ShipmentRepository shipmentRepo;
	private final SimpMessagingTemplate messageTemplate;
	
	public ShipmentDTO.ShipmentResponse createShipment(ShipmentDTO.CreateShipmentRequest request) {
		
		String trackNum = generateTrackingNumber();
		Shipment shipment = Shipment.builder()
				.trackingNumber(trackNum)
				.origin(request.origin)
				.destination(request.destination)
				.estimatedDelivery(request.estimatedDelivery)
				.build();
		
		shipmentRepo.save(shipment);
		notifyShipmentStatus(shipment, getStatusMessage(shipment.getStatus()));
		
		return mapToResponse(shipment);
	}
	
	public List<ShipmentDTO.ShipmentResponse> getAllShipments(){
		List<Shipment> shipments = shipmentRepo.findAll();
		return shipments.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public ShipmentDTO.ShipmentResponse getShipmentById(Long id) {
		Shipment ship = shipmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Shipment with this Id doesn't exist, id: " + id ));
		return mapToResponse(ship);
	}
	
	public ShipmentDTO.ShipmentResponse getShipmentByTrackingNumber(String trackingNumber) {
		Shipment ship = shipmentRepo.findByTrackingNumber(trackingNumber)
				.orElseThrow(()-> new RuntimeException("This tracking number doesn't exist: " + trackingNumber));		
		return mapToResponse(ship);
	}

	public ShipmentResponse updateShipmentStatus(@Valid ShipmentDTO.UpdateStatusRequest request , Long id) {
		Shipment ship = shipmentRepo.findById(id) 
				.orElseThrow(()-> new RuntimeException("Shipment not found with id: " + id));
		ship.setStatus(request.getStatus());
		if(request.getCurrentLocation()!=null) {
			ship.setCurrentLocation(request.getCurrentLocation());
		}
		ship = shipmentRepo.save(ship);
		
		return mapToResponse(ship);
	}
	
	
	private String generateTrackingNumber() {
		return "TRK-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
	}
	
	private ShipmentDTO.ShipmentResponse mapToResponse(Shipment ship){
		return ShipmentDTO.ShipmentResponse.builder()
				.id(ship.getId())
				.trackingNumber(ship.getTrackingNumber())
				.origin(ship.getOrigin())
				.destination(ship.getDestination())
				.status(ship.getStatus())
				.createdAt(ship.getCreatedAt())
				.updatedAt(ship.getUpdatedAt())
				.currentLocation(ship.getCurrentLocation())
				.estimatedDelivery(ship.getEstimatedDelivery())
				.build();
	}

	
	
	public void notifyShipmentStatus(Shipment ship,String message) {
		var update = ShipmentDTO.StatusUpdateMessage.builder()
				.shipmentId(ship.getId())
				.trackingNumber(ship.getTrackingNumber())
				.status(ship.getStatus())
				.currentLocation(ship.getCurrentLocation())
				.timeStamp(ship.getUpdatedAt())
				.message(message)
				.build();
		messageTemplate.convertAndSend("/topic/shipment-status/" + ship.getId(),update);
		
		log.info("Sent shipment status update: {}",update);
	}
	
	private String getStatusMessage(ShipmentStatus status) {
		return switch(status) {
			case ORDER_PLACED -> "Order has been placed";
			case PROCESSING -> "Order is being processed";
			case PICKED_UP-> "Package has been picked up";
			case IN_TRANSIT -> "Package is in transit";
			case OUT_FOR_DELIVERY -> "Package is out for delivery";
			case DELIVERED -> "Package has been delivered";
			case EXCEPTION -> "Delivery exception occurred";
		};
		
	}


}
