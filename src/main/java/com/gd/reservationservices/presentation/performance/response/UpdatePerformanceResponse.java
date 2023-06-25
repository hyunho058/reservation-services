package com.gd.reservationservices.presentation.performance.response;

import com.gd.reservationservices.application.performance.dto.PerformancePlace;
import com.gd.reservationservices.infrastructure.user.user.performance.value.Category;
import com.gd.reservationservices.infrastructure.user.user.performance.value.FilmRating;

import java.time.LocalDateTime;

public record UpdatePerformanceResponse(
    PerformancePlace place,
    Category category,
    LocalDateTime startAt,
    LocalDateTime startReservationAt,
    LocalDateTime endReservationAt,
    String title,
    String content,
    String acting,
    FilmRating filmRating
) {

}
