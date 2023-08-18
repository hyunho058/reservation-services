package com.reservation.application.user.dto;

import com.reservation.domain.user.Role;
import com.reservation.domain.user.User;

public record UpdateUser(
        String userId,
        String name,
        int age,
        String email,
        String phone,
        Role role
) {
    public UpdateUser(User user) {
        this(
            user.getUserId(),
            user.getName(),
            user.getAgw(),
            user.getEmail(),
            user.getPhone(),
            user.getRole()
        );
    }
}
