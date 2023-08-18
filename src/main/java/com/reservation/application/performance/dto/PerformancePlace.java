package com.reservation.application.performance.dto;

import com.reservation.domain.performance.Place;

public record PerformancePlace(
    String name,
    String location
) {
    public PerformancePlace(Place place) {
        this(
            place.getName(),
            place.getLocation()
        );
    }
}
