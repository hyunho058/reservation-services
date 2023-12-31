package com.reservation.application.payment.dto;

import com.reservation.domain.payment.Coupon;

import java.time.LocalDateTime;

public record CreateCouponResult(
        Long id,
        String performanceTitle,
        String serialNumber,
        String type,
        Integer discountValue,
        LocalDateTime expiredAt
) {
    public CreateCouponResult(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getPerformance().getTitle(),
                coupon.getSerialNumber(),
                coupon.getType().toString(),
                coupon.getDiscountValue(),
                coupon.getExpiredAt()
        );
    }
}
