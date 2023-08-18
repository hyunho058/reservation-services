package com.reservation.application.performance.exception;

import com.reservation.common.exception.ErrorCodeException;

public class AlreadyReservedSeatException extends ErrorCodeException {
    public AlreadyReservedSeatException() {
        super(ErrorCode.ALREADY_RESERVED_SEAT);
    }
}
