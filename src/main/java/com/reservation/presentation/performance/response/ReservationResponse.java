package com.reservation.presentation.performance.response;

import com.reservation.domain.performance.Reservation;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.user.User;

import java.time.LocalDateTime;

public record ReservationResponse(
    Long id,
    User user,
    Seat seat,
    LocalDateTime reservedAt
) {
    public ReservationResponse(Reservation reservation) {
        this(
            reservation.getId(),
            reservation.getUser(),
            reservation.getSeat(),
            reservation.getReservedAt()
        );
    }
}
