package com.hotel.hotelease.repository;

import com.hotel.hotelease.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByRoomNumber(int number);

    void deleteByCheckOutBefore(LocalDateTime localDateTime);
}
