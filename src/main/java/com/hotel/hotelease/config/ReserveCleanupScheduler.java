package com.hotel.hotelease.config;

import com.hotel.hotelease.repository.ReserveRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class ReserveCleanupScheduler {
    private final ReserveRepository reserveRepository;

    public ReserveCleanupScheduler(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void deleteOldReserves() {
        reserveRepository.deleteByCheckOutBefore(LocalDateTime.now().minusDays(7));
    }
}