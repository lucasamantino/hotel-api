package com.hotel.hotelease.dto;

public record ReserveRequest(String userEmail, int roomNumber,String checkIn, int days, String status) {

}
