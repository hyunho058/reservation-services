package com.reservation.domain.performance.repository;

import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findAllByPerformance(Performance performance, Pageable pageable);
}
