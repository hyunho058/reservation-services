package com.reservation.application.performance.dto;

import com.reservation.domain.performance.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

public record SearchReservationListResult(
    List<Reservation> reservations,
    Long totalCount,
    Integer currentPage,
    Integer perPage,
    Integer totalPage
) {
    public SearchReservationListResult(Page<Reservation> reservations) {
        this(
            reservations.getContent(),
            reservations.getTotalElements(),
            reservations.getPageable().getPageNumber(),
            reservations.getPageable().getPageSize(),
            reservations.getTotalPages()
        );
    }
}
