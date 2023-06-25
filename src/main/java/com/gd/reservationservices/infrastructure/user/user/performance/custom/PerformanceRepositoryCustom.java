package com.gd.reservationservices.infrastructure.user.user.performance.custom;

import com.gd.reservationservices.domain.performance.Performance;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PerformanceRepositoryCustom {
    boolean exists(Long placeId, LocalDateTime startAt, LocalDateTime endAt);
    Optional<Performance> findPerformanceAndPlace(Long id);
}
