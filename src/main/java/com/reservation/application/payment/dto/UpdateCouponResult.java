package com.reservation.application.payment.dto;

import com.reservation.domain.payment.Coupon;

import java.time.LocalDateTime;

public record UpdateCouponResult(
        Long id,
        String performanceTitle,
        String serialNumber,
        String type,
        Integer discountValue,
        LocalDateTime usedAt,
        LocalDateTime expiredAt
) {
    public UpdateCouponResult(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getPerformance().getTitle(),
                coupon.getSerialNumber(),
                coupon.getType().toString(),
                coupon.getDiscountValue(),
                coupon.getUsedAt(),
                coupon.getExpiredAt()
        );
    }
}
