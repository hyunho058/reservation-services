package com.reservation.presentation.payment.response;

import com.reservation.application.payment.dto.UpdateCouponResult;

import java.time.LocalDateTime;

public record UpdateCouponResponse(
        Long id,
        String performanceTitle,
        String serialNumber,
        String type,
        Integer discountValue,
        LocalDateTime usedAt,
        LocalDateTime expiredAt
) {
    public UpdateCouponResponse(UpdateCouponResult coupon) {
        this(
                coupon.id(),
                coupon.performanceTitle(),
                coupon.serialNumber(),
                coupon.type(),
                coupon.discountValue(),
                coupon.usedAt(),
                coupon.expiredAt()
        );
    }
}
