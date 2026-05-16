# Shipment Tracker

A full-stack shipment tracking system that allows users to create shipments, update their statuses in real-time, and view all shipments in a responsive grid. The application uses **Angular** for the frontend and **Spring Boot** with WebSockets for the backend, providing live updates and a seamless user experience.

![Shipment Tracker Dashboard]
*Main dashboard showing shipments with status, timestamps, origin/destination, and estimated duration.*

---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
  - [Frontend (Angular)](#frontend-angular)
  - [Backend (Spring Boot)](#backend-spring-boot)
- [Screenshots](#screenshots)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
---

## Features

- **Create Shipments** – Enter origin, destination, estimated delivery, and current location.
- **Update Status** – Change a shipment’s status (Order Placed, Processing, Exception, etc.).
- **Real‑time Updates** – WebSocket pushes changes to all connected clients instantly.
- **Responsive Grid** – Displays all shipments with status badges, timestamps, and route info.
- **Clean UI** – Intuitive dashboard with two main actions: create and update.

---

## Technologies

| Layer       | Technology                                                                 |
|-------------|----------------------------------------------------------------------------|
| Frontend    | Angular 17+, TypeScript, HTML5, CSS3                                       |
| Backend     | Spring Boot 3, Spring Data JPA, Hibernate, WebSocket (STOMP)               |
| Database    | H2 (in‑memory) – can be swapped for PostgreSQL/MySQL                       |
| Build Tools | Maven (backend), Angular CLI (frontend)                                    |
| Other       | Java 21, JWT-ready architecture, REST APIs                                 |

---

## Project Structure

### Frontend (Angular)
shipment-fe/

├── .angular/

├── .vscode/

├── node_modules/

├── public/

├── src/

│ ├── app/

│ │ ├── create-shipment/ # Create form component

│ │ ├── header/ # Site header component

│ │ ├── models/ # TypeScript interfaces/models

│ │ ├── notification/ # Toast/notification service

│ │ ├── service/ # API and WebSocket services

│ │ ├── shipment-grid/ # Grid view component

│ │ ├── update-shipment/ # Update status form

│ │ ├── app.config.ts

│ │ ├── app.css

│ │ ├── app.html

│ │ ├── app.routes.ts

│ │ └── app.ts

│ ├── index.html

│ ├── main.ts

│ └── styles.css

├── angular.json

├── package.json

└──..


### Backend (Spring Boot)
shipmenttracker/

├── src/main/java/com/azim/shipmenttracker/

│ ├── ShipmentTrackerApplication.java

│ ├── config/

│ │ ├── CorsConfig.java

│ │ └── WebSocketConfig.java

│ ├── shipment/

│ │ ├── Shipment.java # Entity

│ │ ├── ShipmentController.java # REST endpoints

│ │ ├── ShipmentDTO.java # Data transfer object

│ │ ├── ShipmentRepository.java # JPA repository

│ │ ├── ShipmentService.java # Business logic

│ │ └── ShipmentStatus.java # Enum (ORDER_PLACED, PROCESSING, EXCEPTION...)

├── src/main/resources/

├── pom.xml

└── ...


## Screenshots

**Create & Update Forms**  
<img width="1686" height="492" alt="image" src="https://github.com/user-attachments/assets/a50518cf-88ad-442d-bf1f-a25030f5b786" />
*Left: create a new shipment with origin/destination. Right: select an existing shipment and update its status.*

**Live Tracking Grid**  
<img width="1897" height="1069" alt="image" src="https://github.com/user-attachments/assets/fdb677f5-fc03-4173-a182-1379cfa1f8e6" />
*All shipments appear with their unique tracking ID, status, timestamps, origin/destination arrows, and duration estimates. The “Connected” indicator shows WebSocket is active.*

---

## Prerequisites

- **Java 21** or later
- **Node.js** 18+ and **npm** (or yarn)
- **Angular CLI** (`npm install -g @angular/cli`)
- **Maven** (or use the included Maven wrapper)

### API endpoints

---

| Method | Endpoint                     | Description                          |
|--------|------------------------------|--------------------------------------|
| GET    | /api/shipments               | Get all shipments                    |
| GET    | /api/shipments/{id}          | Get one shipment by ID               |
| POST   | /api/shipments               | Create a new shipment                |
| PUT    | /api/shipments/{id}/status   | Update status of a shipment          |
| DELETE | /api/shipments/{id}          | Delete a shipment (optional)         |
