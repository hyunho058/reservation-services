package com.reservation.application.performance.dto;

public record CreateReservationValue(
    Long userId,
    String seatLocation,
    Integer seatNumber
) {
}
