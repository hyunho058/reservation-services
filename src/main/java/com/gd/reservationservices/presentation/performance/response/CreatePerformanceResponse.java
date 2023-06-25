package com.gd.reservationservices.presentation.performance.response;

import com.gd.reservationservices.application.performance.dto.CreatePerformance;
import com.gd.reservationservices.application.performance.dto.PerformancePlace;
import com.gd.reservationservices.domain.performance.Performance;
import com.gd.reservationservices.infrastructure.user.user.performance.value.Category;
import com.gd.reservationservices.infrastructure.user.user.performance.value.FilmRating;

import java.time.LocalDateTime;

public record CreatePerformanceResponse(
    PerformancePlace place,
    Category category,
    LocalDateTime startAt,
    LocalDateTime startReservationAt,
    LocalDateTime endReservationAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String title,
    String content,
    String acting,
    FilmRating filmRating
) {
    public CreatePerformanceResponse(CreatePerformance createPerformance) {
        this(
            createPerformance.place(),
            createPerformance.category(),
            createPerformance.startAt(),
            createPerformance.startReservationAt(),
            createPerformance.endReservationAt(),
            createPerformance.createdAt(),
            createPerformance.updatedAt(),
            createPerformance.title(),
            createPerformance.content(),
            createPerformance.acting(),
            createPerformance.filmRating()
        );
    }
}
