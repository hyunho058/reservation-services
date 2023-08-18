package com.reservation.presentation.user.response;

import com.reservation.application.user.dto.UpdateUserResult;
import com.reservation.domain.user.Role;

public record UpdateUserResponse(
    String userId,
    String name,
    int age,
    String email,
    String phone,
    Role role
) {
    public UpdateUserResponse(UpdateUserResult updateUserResult) {
        this(
            updateUserResult.userId(),
            updateUserResult.name(),
            updateUserResult.age(),
            updateUserResult.email(),
            updateUserResult.phone(),
            updateUserResult.role()
        );
    }
}
