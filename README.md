# Parking Service

A small, focused backend service that manages parking spots and bookings for residential communities.  
Designed as a drop-in service that with some modifications can be integrated into a larger application. 
Built with Spring Boot, JPA/Hibernate and PostgreSQL.

## Overview
This service exposes REST-ful APIs to:
- manage parking spots (CRUD),
- search/filter parking spots (including availability for a time window),
- create bookings, including *recurring* bookings,
- view bookings (current/future/history).

It is intentionally built as a focused microservice: business logic and availability checks live here; 
**authentication / authorization (security) is expected to be handled by the host application.**


## Key features
- CRUD for `ParkingSpot`, `Booking`, `ResidentialCommunity`, `Membership` (basic).
- Filterable and sortable search  with pagination support
  - for parking spots (by `spotNumber`, `type`, `status`, `residentialCommunityId`, and availability window).
  - for bookings (by `userId`, `parkingSpotId`, `status`, `createdAt`, `updatedAt`, and time interval).
- Availability checks are dynamic (derived from bookings), with a configurable 5-minute safety buffer on both sides of the requested interval.
- Recurring bookings support (`NONE`, `EVERY_DAY`, `EVERY_WEEK`, `EVERY_MONTH`) with `repeatCount`.
- Basic validations (no overlapping bookings, start >= now, start <= end).
- Lightweight test examples and Postman collection (see `TESTING`).

 
## Core design & assumptions
- **Availability is derived** from bookings — a spot's `status` only reflects physical state (`AVAILABLE`, `MAINTENANCE`); occupancy is computed by checking bookings against a requested interval.
- **Buffering:** To enforce small gaps, the requested interval is expanded by 5 minutes on each side before overlap checks.
- **Bookings that block availability:** only bookings with `BookingStatus.BOOKED` or `BookingStatus.PARKED` block availability; `COMPLETED` or `CANCELLED` do not.
- **User management & security:** the repo contains a `User` entity for relationships and tests, but **auth is expected to be provided by the integrating application**.
- **Residential Communities and Membership:** these entities along with their CRUDs are developed for demo and testing purposes, when integrating some modifications are to be made for the service to work with the existing applications structure.
- **Default interval behavior:** if `startTime` is omitted -> `now`; if `endTime` omitted → `startTime + 1 hour`. If repetition count is not specified -> 5 times for recurring bookings.


## Testing
- By clicking this [link](https://web.postman.co/workspace/Parking-Service~889966b4-3fc0-4902-8284-0de0819c1cd7/collection/43259412-e8fec11b-94e8-4524-9568-3bc98ad77bed?action=share&source=copy-link&creator=43259412) you will access my **Postman workspace**.
- Testing cases for each API are grouped in corresponding folders, feel free to go through them all. 


## Acknowledgments
This project was developed as part of an internship application task.
This README file and some commit messages' wording were refined with the assistance of AI tools.
Note, all design and implementation decisions were made and coded by **me**.