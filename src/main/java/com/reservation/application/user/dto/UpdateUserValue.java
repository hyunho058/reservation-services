package com.reservation.application.user.dto;

public record UpdateUserValue(
        String userPw,
        String name,
        int age
) {
}
