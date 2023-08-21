package com.reservation.presentation.user.response;

import com.reservation.application.user.dto.SavedUserValue;
import com.reservation.infrastructure.user.value.Role;

public record SavedUserResponse(
        String userId,
        String name,
        int age,
        String email,
        String phone,
        Role role
) {
    public SavedUserResponse(SavedUserValue savedUser) {
        this(
                savedUser.userId(),
                savedUser.name(),
                savedUser.age(),
                savedUser.email(),
                savedUser.phone(),
                savedUser.role()
        );
    }
}
