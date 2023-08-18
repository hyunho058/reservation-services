package com.reservation.domain.performance.repository;

import com.reservation.domain.performance.Performance;
import com.reservation.infrastructure.performance.custom.PerformanceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryCustom {
}
