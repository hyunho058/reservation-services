package com.gd.reservationservices.presentation.user.request;

import com.gd.reservationservices.application.user.dto.UpdateUserCommend;

public record UpdateUserRequest(
    String userPw,
    String name,
    int age
) {
    public UpdateUserCommend toValue() {
        return new UpdateUserCommend(
            this.userPw,
            this.name,
            this.age
        );
    }
}
