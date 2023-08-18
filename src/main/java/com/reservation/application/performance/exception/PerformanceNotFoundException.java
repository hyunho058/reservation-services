package com.reservation.application.performance.exception;

import com.reservation.common.exception.ErrorCodeException;

public class PerformanceNotFoundException extends ErrorCodeException {
    public PerformanceNotFoundException() {
        super(ErrorCode.PERFORMANCE_NOT_FOUND);
    }
}
