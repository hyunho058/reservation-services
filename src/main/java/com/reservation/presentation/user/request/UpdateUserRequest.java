package com.reservation.presentation.user.request;

import com.reservation.application.user.dto.UpdateUserValue;

public record UpdateUserRequest(
    String userPw,
    String name,
    int age
) {
    public UpdateUserValue toValue() {
        return new UpdateUserValue(
            this.userPw,
            this.name,
            this.age
        );
    }
}
