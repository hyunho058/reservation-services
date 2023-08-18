package com.reservation.presentation.user.response;

import com.reservation.application.user.dto.SearchUserResult;
import com.reservation.infrastructure.user.value.Role;

public record SearchUserResponse(
    String userId,
    String name,
    int age,
    String email,
    String phone,
    Role role
) {
    public SearchUserResponse(SearchUserResult searchUserResult) {
        this(
            searchUserResult.userId(),
            searchUserResult.name(),
            searchUserResult.age(),
            searchUserResult.email(),
            searchUserResult.phone(),
            searchUserResult.role()
        );
    }
}
