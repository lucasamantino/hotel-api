package com.hotel.hotelease.controller;

import com.hotel.hotelease.dto.RoomRequest;
import com.hotel.hotelease.entity.Room;
import com.hotel.hotelease.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms/all")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<List<Room>> listAllRooms () {
        List<Room> rooms = roomService.listAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/free")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST') or hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<List<Room>> listFreeRooms () {
        List<Room> rooms = roomService.listFreeRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_RECEPTIONIST')")
    public ResponseEntity<List<Room>> listRoomType (@RequestParam String type) {
        List<Room> rooms = roomService.listRoomType(type);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/rooms")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> createRoom (@RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @PutMapping("/rooms/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> updateRoom (@RequestBody RoomRequest request, @PathVariable Long id) {
        int resp = roomService.updateRoom(request,id);
        switch (resp) {
            case 1:
                return ResponseEntity.badRequest().build();
            case 2:
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Room updated!");
    }

    @DeleteMapping("/rooms/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteRoom (@PathVariable Long id) {
        if (!roomService.deleteRoom(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Room deleted!");
    }


}
