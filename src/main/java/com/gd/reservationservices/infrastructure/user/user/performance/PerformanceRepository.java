package com.gd.reservationservices.infrastructure.user.user.performance;

import com.gd.reservationservices.domain.performance.Performance;
import com.gd.reservationservices.infrastructure.user.user.performance.custom.PerformanceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryCustom {
}
