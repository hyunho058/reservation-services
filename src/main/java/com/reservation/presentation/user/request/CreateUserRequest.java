package com.reservation.presentation.user.request;

import com.reservation.application.user.command.CreateUser;

public record CreateUserRequest(
    String userId,
    String userPw,
    String name,
    int age,
    String email,
    String phone,
    String role
) {
    public CreateUser toValue() {
        return new CreateUser(
            this.userId,
            this.userPw,
            this.name,
            this.age,
            this.email,
            this.phone,
            this.role
        );
    }
}
