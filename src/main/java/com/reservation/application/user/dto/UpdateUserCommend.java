package com.reservation.application.user.dto;

public record UpdateUserCommend(
        String userPw,
        String name,
        int age
) {
}
