package com.reservation.application.performance.exception;

import com.reservation.common.exception.ErrorCodeException;

public class ReservationNotFoundException extends ErrorCodeException {
    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
