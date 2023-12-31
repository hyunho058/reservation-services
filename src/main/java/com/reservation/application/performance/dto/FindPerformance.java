package com.reservation.application.performance.dto;

import com.reservation.domain.performance.Performance;
import com.reservation.infrastructure.performance.value.Category;
import com.reservation.infrastructure.performance.value.FilmRating;

import java.time.LocalDateTime;

public record FindPerformance(
    Long id,
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
    public FindPerformance(Performance performance, PerformancePlace place) {
        this(
            performance.getId(),
            place,
            Category.valueOf(performance.getCategory().toString()),
            performance.getStartAt(),
            performance.getStartReservationAt(),
            performance.getEndReservationAt(),
            performance.getCreatedAt(),
            performance.getUpdatedAt(),
            performance.getTitle(),
            performance.getContent(),
            performance.getActing(),
            FilmRating.valueOf(performance.getFilmRating().toString())
        );
    }
}
