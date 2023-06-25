package com.gd.reservationservices.application.user.exception;

import com.gd.reservationservices.application.exception.ErrorCodeException;

public class UserNotFoundException extends ErrorCodeException {
    public UserNotFoundException() {
        super(ErrorCodeException.ErrorCode.USER_NOT_FOUND);
    }
}
