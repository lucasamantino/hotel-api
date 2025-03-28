package com.hotel.hotelease.repository;

import com.hotel.hotelease.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNumber(int number);
    List<Room> findByStatus(String status);
    List<Room> findByRoomType(String status);
    Room findByNumber(int number);
}
