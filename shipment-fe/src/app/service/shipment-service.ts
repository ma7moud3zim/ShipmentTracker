import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateShipmentRequest, Shipment } from '../models/shipment.model';

@Injectable({
  providedIn: 'root',
})
export class ShipmentService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/shipments';

  createShipment(request: CreateShipmentRequest):Observable<Shipment> {
    return this.http.post<Shipment>(this.apiUrl, request);
  }

  getAllShipments():Observable<Shipment[]> {
    return this.http.get<Shipment[]>(this.apiUrl);
  }

  getAllShipmentById(id: number):Observable<Shipment> {
    return this.http.get<Shipment>(`${this.apiUrl}/track/${id}`);
  }

  getShipmentByTrackingNumber(trackingNumber: string):Observable<Shipment> {
    return this.http.get<Shipment>(`${this.apiUrl}/track/${trackingNumber}`);
  }
  updateShipment(id: number, request: any):Observable<Shipment> {
    return this.http.put<Shipment>(`${this.apiUrl}/${id}/status`, request);
  }
}
