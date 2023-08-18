package com.reservation.application.performance.exception;

import com.reservation.common.exception.ErrorCodeException;

public class ReservationNotMatchedException extends ErrorCodeException {
    public ReservationNotMatchedException() {
        super(ErrorCode.RESERVATION_NOT_MATCHED_PERFORMANCE);
    }
}
