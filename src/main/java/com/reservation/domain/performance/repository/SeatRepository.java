package com.reservation.domain.performance.repository;

import com.reservation.domain.performance.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByPerformanceIdAndLocationAndNumber(Long performanceId,
                                                           String location,
                                                           Integer number);
}
