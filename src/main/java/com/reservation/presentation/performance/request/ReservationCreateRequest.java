package com.reservation.presentation.performance.request;

import com.reservation.application.performance.dto.CreateReservationValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationCreateRequest (
    @NotNull Long userId,
    @NotBlank String seatLocation,
    @Positive Integer seatNumber
) {
    public CreateReservationValue toValue() {
        return new CreateReservationValue(
            this.userId, this.seatLocation, this.seatNumber
        );
    }
}
