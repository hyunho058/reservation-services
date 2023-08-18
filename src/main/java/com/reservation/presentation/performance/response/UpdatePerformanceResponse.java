package com.reservation.presentation.performance.response;

import com.reservation.application.performance.dto.PerformancePlace;
import com.reservation.infrastructure.performance.value.Category;
import com.reservation.infrastructure.performance.value.FilmRating;

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
