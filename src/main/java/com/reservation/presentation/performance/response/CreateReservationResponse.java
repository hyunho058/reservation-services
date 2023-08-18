package com.reservation.presentation.performance.response;

import com.reservation.application.performance.dto.CreateReservationResult;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.user.User;

import java.time.LocalDateTime;

public record CreateReservationResponse (
    Long id,
    User user,
    Performance performance,
    Seat seat,
    LocalDateTime reservedAt
) {
    public CreateReservationResponse(CreateReservationResult result) {
        this(
            result.id(),
            result.user(),
            result.performance(),
            result.seat(),
            result.reservedAt()
        );
    }
}
