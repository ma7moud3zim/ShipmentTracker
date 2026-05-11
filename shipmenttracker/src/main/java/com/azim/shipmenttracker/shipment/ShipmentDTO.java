package com.azim.shipmenttracker.shipment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ShipmentDTO {
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateShipmentRequest{
		@NotBlank(message = "Origin is Required")
		public String origin;
		
		@NotBlank(message = "Destiation is Required")
		public String destination;
		
		public String estimatedDelivery;
	}
	
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ShipmentResponse{
		private long id;	
		private String trackingNumber;	
		private String origin;
		private String destination;
		private ShipmentStatus status;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private String currentLocation;	
		private String estimatedDelivery;
		
	}

	
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateStatusRequest{
		private ShipmentStatus status;
		private String currentLocation;	
		
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StatusUpdateMessage{
		private Long shipmentId;	
		private String trackingNumber;	
		private ShipmentStatus status;
		private String currentLocation;	
		private LocalDateTime timeStamp;
		private String message;
	}
	
}
