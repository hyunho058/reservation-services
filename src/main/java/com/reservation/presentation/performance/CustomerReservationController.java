package com.reservation.presentation.performance;

import com.reservation.application.performance.ReservationService;
import com.reservation.application.performance.dto.CreateReservationResult;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.performance.request.ReservationCreateRequest;
import com.reservation.presentation.performance.response.CreateReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/performances")
@RequiredArgsConstructor
public class CustomerReservationController {
    private final ReservationService reservationService;

    @PostMapping("/{performanceId}/reservations")
    @ResponseStatus(HttpStatus.OK)
    public SingleResponse<CreateReservationResponse> create(@PathVariable Long performanceId,
                                                            @RequestBody ReservationCreateRequest request) {
        CreateReservationResult result = reservationService.create(performanceId, request.toValue());

        return new SingleResponse.Ok<>(
            new CreateReservationResponse(result)
        );
    }
}
