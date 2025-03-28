package com.hotel.hotelease.service;

import com.hotel.hotelease.dto.RoomRequest;
import com.hotel.hotelease.entity.Room;
import com.hotel.hotelease.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private final String[] roomTypes = {"STANDARD","MASTER","DELUXE"};
    private final String[] roomStatus = {"FREE","BUSY","MAINTENANCE"};

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String createRoom (RoomRequest request) {
        if (roomRepository.existsByNumber(request.number())) {
            return "Room number already created!";
        }
        if (!Arrays.asList(roomTypes).contains(request.roomType())) {
            return "Unknown room type!";
        }

        Room room = new Room();
        room.setPrice(request.price());
        room.setNumber(request.number());
        room.setStatus(roomStatus[0]);
        room.setRoomType(request.roomType());

        roomRepository.save(room);
        return "Room created!";
    }

    public List<Room> listAllRooms () {
        return roomRepository.findAll();
    }

    public List<Room> listFreeRooms () {
        return roomRepository.findByStatus("FREE");
    }

    public List<Room> listRoomType (String type) {
        return roomRepository.findByRoomType(type);
    }

    public int updateRoom (RoomRequest request, Long id) {
        if (!Arrays.asList(roomTypes).contains(request.roomType())) {
            return 1;
        }
        if (roomRepository.findById(id).isEmpty()) {
            return 2;
        }

        Room room = new Room();
        room.setPrice(request.price());
        room.setNumber(request.number());
        room.setStatus(request.status());
        room.setRoomType(request.roomType());
        room.setId(id);

        roomRepository.save(room);
        return 0;
    }

    public boolean deleteRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return false;
        }

        roomRepository.deleteById(id);
        return true;
    }
}
