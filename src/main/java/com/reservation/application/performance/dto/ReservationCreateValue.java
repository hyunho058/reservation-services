package com.reservation.application.performance.dto;

public record ReservationCreateValue (
    Long userId,
    String seatLocation,
    Integer seatNumber
) {
}
