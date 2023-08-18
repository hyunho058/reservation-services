package com.reservation.presentation.performance.response;

import com.reservation.application.performance.dto.SearchReservationResult;
import com.reservation.domain.performance.Reservation;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.user.User;

import java.time.LocalDateTime;

public record SearchReservationResponse(
    Long id,
    User user,
    Seat seat,
    LocalDateTime reservedAt
) {
    public SearchReservationResponse(Reservation reservation) {
        this(
            reservation.getId(),
            reservation.getUser(),
            reservation.getSeat(),
            reservation.getReservedAt()
        );
    }

    public SearchReservationResponse(SearchReservationResult result) {
        this(
            result.id(),
            result.user(),
            result.seat(),
            result.reservedAt()
        );
    }
}
