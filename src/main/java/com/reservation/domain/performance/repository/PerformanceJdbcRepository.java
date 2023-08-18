package com.reservation.domain.performance.repository;

import com.reservation.domain.performance.Seat;

import java.util.List;

public interface PerformanceJdbcRepository {
    void saveAll(List<Seat> seats);
}
