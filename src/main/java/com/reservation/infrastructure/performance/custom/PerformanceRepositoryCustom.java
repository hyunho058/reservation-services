package com.reservation.infrastructure.performance.custom;

import com.reservation.domain.performance.Performance;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PerformanceRepositoryCustom {
    boolean exists(Long placeId, LocalDateTime startAt, LocalDateTime endAt);
    Optional<Performance> findPerformanceAndPlace(Long id);
}
