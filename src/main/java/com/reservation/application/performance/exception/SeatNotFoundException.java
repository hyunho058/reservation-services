package com.reservation.application.performance.exception;

import com.reservation.common.exception.ErrorCodeException;

public class SeatNotFoundException extends ErrorCodeException {
    public SeatNotFoundException() {
        super(ErrorCode.SEAT_NOT_FOUND);
    }
}
