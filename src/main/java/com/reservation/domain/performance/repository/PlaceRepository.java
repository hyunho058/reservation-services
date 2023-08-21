package com.reservation.domain.performance.repository;

import com.reservation.domain.performance.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> wfindById(Long id);
}
