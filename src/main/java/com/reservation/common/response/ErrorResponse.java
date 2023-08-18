package com.reservation.common.response;


import com.reservation.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final Meta meta;

    public ErrorResponse(String errorCode, String errorMessage) {
        this.meta = new Meta.Fail(errorCode, errorMessage);
    }

    public static ErrorResponse of(String errorCodeName) {
        ErrorCode errorCode = ErrorCode.valueOf(errorCodeName);
        return new ErrorResponse(errorCode.getKey(), errorCode.getMessage());
    }
}
