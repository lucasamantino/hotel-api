package com.hotel.hotelease.service;

import com.hotel.hotelease.dto.ReserveRequest;
import com.hotel.hotelease.dto.EditReserveResponse;
import com.hotel.hotelease.entity.Reserve;
import com.hotel.hotelease.entity.Room;
import com.hotel.hotelease.entity.User;
import com.hotel.hotelease.repository.ReserveRepository;
import com.hotel.hotelease.repository.RoomRepository;
import com.hotel.hotelease.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final String[] reserveStatus = {"CONFIRMED","CANCELED","ACTIVE","ENDED"};

    public ReserveService(ReserveRepository reserveRepository,
                          UserRepository userRepository,
                          RoomRepository roomRepository) {
        this.reserveRepository = reserveRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public EditReserveResponse createReserve(ReserveRequest reserveRequest) {
        if (!testDate(reserveRequest.checkIn())) {
            return new EditReserveResponse("Date unavailable!",false);
        }
        if (reserveRequest.days() <= 0) {
            return new EditReserveResponse("Invalid duration!",false);
        }

        User user = userRepository.findByEmail(reserveRequest.userEmail());
        Room room = roomRepository.findByNumber(reserveRequest.roomNumber());
        Reserve reserve = new Reserve();
        List<Reserve> oldReserves = reserveRepository.findByRoomNumber(reserveRequest.roomNumber());

        if (user == null || room == null) {
            return new EditReserveResponse("User or room incorrect!",false);
        }
        if (!room.getStatus().equals("FREE")) {
            return new EditReserveResponse("Room unavailable!",false);
        }

        reserve.setCheckIn(LocalDateTime.parse(reserveRequest.checkIn()));
        reserve.setCheckOut(LocalDateTime.parse(reserveRequest.checkIn()).plusDays(reserveRequest.days()));

        if (!oldReserves.isEmpty()) {
            for (Reserve r : oldReserves) {
                if (!r.getStatus().equals(reserveStatus[1]) && !r.getStatus().equals(reserveStatus[3]) && reserve.getCheckOut().isAfter(r.getCheckIn()) && reserve.getCheckIn().isBefore(r.getCheckOut().plusHours(5))) {
                    return new EditReserveResponse("Room unavailable during the period!",false);
                }
            }
        }

        reserve.setUser(user);
        reserve.setRoom(room);
        reserve.setStatus(reserveStatus[0]);

        reserveRepository.save(reserve);

        return new EditReserveResponse("Reserve created!",true);
    }

    public EditReserveResponse updateReserve(ReserveRequest reserveRequest, Long id) {
        if (!testDate(reserveRequest.checkIn())) {
            return new EditReserveResponse("Date unavailable!",false);
        }
        if (reserveRequest.days() <= 0) {
            return new EditReserveResponse("Invalid duration!",false);
        }

        User user = userRepository.findByEmail(reserveRequest.userEmail());
        Room room = roomRepository.findByNumber(reserveRequest.roomNumber());
        Reserve reserve = new Reserve();
        reserve.setId(id);
        List<Reserve> oldReserves = reserveRepository.findByRoomNumber(reserveRequest.roomNumber());

        if (user == null || room == null) {
            return new EditReserveResponse("User or room incorrect!",false);
        }
        if (!room.getStatus().equals("FREE")) {
            return new EditReserveResponse("Room unavailable!",false);
        }

        reserve.setCheckIn(LocalDateTime.parse(reserveRequest.checkIn()));
        reserve.setCheckOut(LocalDateTime.parse(reserveRequest.checkIn()).plusDays(reserveRequest.days()));

        if (!oldReserves.isEmpty()) {
            for (Reserve r : oldReserves) {
                if (!r.getStatus().equals(reserveStatus[1]) && !r.getStatus().equals(reserveStatus[3]) && !r.getId().equals(reserve.getId()) && reserve.getCheckOut().isAfter(r.getCheckIn()) && reserve.getCheckIn().isBefore(r.getCheckOut().plusHours(5))) {
                    return new EditReserveResponse("Room unavailable during the period!",false);
                }
                if (r.getId().equals(reserve.getId())) {
                    reserve.setStatus(r.getStatus());
                }
            }
        }

        reserve.setUser(user);
        reserve.setRoom(room);
        reserveRepository.save(reserve);

        return new EditReserveResponse("Reserve created!",true);
    }

    private boolean testDate (String dateStr) {
        try {
            LocalDateTime.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Optional<Reserve> getReserve(Long id) {
        Optional<Reserve> reserve = reserveRepository.findById(id);
        return reserve;

    }

    public String deleteReserve(Long id) {
        if (reserveRepository.existsById(id)) {
            Reserve reserve = reserveRepository.findById(id).get();
            reserve.setStatus(reserveStatus[1]);
            reserveRepository.save(reserve);
        } else {
            return "Reserve not found!";
        }
        return "Reserve canceled!";
    }

    public String checkIn(Long id) {
        if (reserveRepository.existsById(id)) {
            Reserve reserve = reserveRepository.findById(id).get();
            if (reserve.getStatus().equals(reserveStatus[1]) || reserve.getStatus().equals(reserveStatus[3])) {
                return "CheckIn error!";
            }
            reserve.setStatus(reserveStatus[2]);
            reserveRepository.save(reserve);
        } else {
            return "CheckIn error!";
        }
        return "CheckIn";
    }

    public String checkOut(Long id) {
        if (reserveRepository.existsById(id)) {
            Reserve reserve = reserveRepository.findById(id).get();
            reserve.setStatus(reserveStatus[3]);
            reserveRepository.save(reserve);
        } else {
            return "CheckOut error!";
        }
        return "CheckOut!";
    }

}
