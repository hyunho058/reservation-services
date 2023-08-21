package com.reservation.application.user.dto;

import com.reservation.domain.user.Role;
import com.reservation.domain.user.User;

public record UpdateUserResult(
        String userId,
        String name,
        int age,
        String email,
        String phone,
        Role role
) {
    public UpdateUserResult(User user) {
        this(
            user.getUserId(),
            user.getName(),
            user.getAge(),
            user.getEmail(),
            user.getPhone(),
            user.getRole()
        );
    }
}
