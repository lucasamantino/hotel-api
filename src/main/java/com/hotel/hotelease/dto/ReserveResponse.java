package com.hotel.hotelease.dto;

import java.time.LocalDateTime;

public record ReserveResponse(LocalDateTime checkIn, LocalDateTime checkOut, String clientname, int roomnumber, String status) {
}
