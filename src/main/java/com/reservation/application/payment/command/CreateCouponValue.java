package com.reservation.application.payment.command;

import com.reservation.domain.payment.Coupon;

import java.time.LocalDateTime;

public record CreateCouponValue(
        Long performanceId,
        Coupon.Type type,
        Integer value,
        LocalDateTime expiredAt,
        Integer amount) {

}