package com.reservation.application.user.exception;


import com.reservation.common.exception.ErrorCodeException;

public class UserNotFoundException extends ErrorCodeException {
    public UserNotFoundException() {
        super(ErrorCodeException.ErrorCode.USER_NOT_FOUND);
    }
}
