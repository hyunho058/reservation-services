package com.reservation.presentation.performance.request;

import com.reservation.application.performance.dto.CreateReservationValue;

public record ReservationCreateRequest (
    Long userId,
    String seatLocation,
    Integer seatNumber
) {
    public CreateReservationValue toValue() {
        return new CreateReservationValue(
            this.userId, this.seatLocation, this.seatNumber
        );
    }
}
