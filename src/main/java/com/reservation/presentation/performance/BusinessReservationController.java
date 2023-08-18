package com.reservation.presentation.performance;

import com.reservation.application.performance.ReservationService;
import com.reservation.application.performance.dto.SearchReservationListResult;
import com.reservation.application.performance.dto.SearchReservationResult;
import com.reservation.common.request.PagingRequest;
import com.reservation.common.response.ListResponse;
import com.reservation.common.response.Paging;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.performance.response.SearchReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/business/performances")
@RequiredArgsConstructor
public class BusinessReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{performanceId}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<SearchReservationResponse> searchAllBy(@PathVariable Long performanceId,
                                                               PagingRequest pagingRequest) {
        SearchReservationListResult reservation = reservationService.searchAllBy(performanceId, pagingRequest.toPageable());

        return new ListResponse.Ok<>(
            reservation.reservations().stream()
                .map(SearchReservationResponse::new)
                .collect(Collectors.toList()),
            new Paging(
                reservation.totalCount(),
                reservation.currentPage(),
                reservation.perPage(),
                reservation.totalPage()
            )
        );
    }

    @GetMapping("/{performanceId}/reservations/{reservationId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResponse<SearchReservationResponse> searchBy(@PathVariable Long performanceId,
                                                              @PathVariable Long reservationId) {
        SearchReservationResult reservation = reservationService.searchBy(performanceId, reservationId);

        return new SingleResponse.Ok<>(
            new SearchReservationResponse(reservation)
        );
    }
}
