package com.hotel.hotelease.controller;

import com.hotel.hotelease.service.ReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CheckController {

    private final ReserveService reserveService;

    public CheckController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping("/checkin/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<String> checkIn (@PathVariable Long id) {
        return ResponseEntity.ok(reserveService.checkIn(id));
    }
    @PostMapping("/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<String> checkOut (@PathVariable Long id) {
        return ResponseEntity.ok(reserveService.checkOut(id));
    }

}
