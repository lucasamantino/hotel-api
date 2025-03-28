package com.hotel.hotelease.controller;

import com.hotel.hotelease.dto.ReserveRequest;
import com.hotel.hotelease.dto.EditReserveResponse;
import com.hotel.hotelease.dto.ReserveResponse;
import com.hotel.hotelease.entity.Reserve;
import com.hotel.hotelease.service.ReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping
public class ReserveController {
    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping("/reservations")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST') or hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<?> createReserve (@RequestBody ReserveRequest reserveRequest) {
        EditReserveResponse editReserveResponse = reserveService.createReserve(reserveRequest);
        return ResponseEntity.ok(editReserveResponse);
    }

    @PutMapping("/reservations/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<?> updateReserve (@RequestBody ReserveRequest reserveRequest, @PathVariable Long id) {
        return ResponseEntity.ok(reserveService.updateReserve(reserveRequest, id));
    }

    @DeleteMapping("/reservations/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<String> deleteReserve (@PathVariable Long id) {
        return ResponseEntity.ok(reserveService.deleteReserve(id));
    }

    @GetMapping("/reservations/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST') or hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<?> getReserve (@PathVariable Long id) {
        Optional<Reserve> reserve = reserveService.getReserve(id);
        if (reserve.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ReserveResponse reserveResponse = new ReserveResponse(reserve.get().getCheckIn(),
                reserve.get().getCheckOut(),
                reserve.get().getUser().getName(),
                reserve.get().getRoom().getNumber(),
                reserve.get().getStatus());
        return ResponseEntity.ok(reserveResponse);
    }

}
