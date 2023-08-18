package com.reservation.application.performance.dto;

import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Reservation;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.user.User;

import java.time.LocalDateTime;

public record CreateReservationResult (
    Long id,
    User user,
    Performance performance,
    Seat seat,
    LocalDateTime reservedAt
) {
    public CreateReservationResult(Reservation reservation) {
        this(
            reservation.getId(),
            reservation.getUser(),
            reservation.getPerformance(),
            reservation.getSeat(),
            reservation.getReservedAt()
        );
    }
}
