package com.reservation.application.performance.dto;

import com.reservation.domain.performance.Reservation;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.user.User;

import java.time.LocalDateTime;

public record SearchReservationResult(
    Long id,
    User user,
    Seat seat,
    LocalDateTime reservedAt
) {
    public SearchReservationResult(Reservation reservation) {
        this(
            reservation.getId(),
            reservation.getUser(),
            reservation.getSeat(),
            reservation.getReservedAt()
        );
    }
}
