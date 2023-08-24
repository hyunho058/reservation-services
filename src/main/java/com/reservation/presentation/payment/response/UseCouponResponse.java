package com.reservation.presentation.payment.response;

import com.reservation.application.payment.dto.UseCouponResult;

import java.time.LocalDateTime;

public record UseCouponResponse(
        Long id,
        String serialNumber,
        String type,
        Integer discountValue,
        LocalDateTime usedAt,
        LocalDateTime expiredAt
) {
    public UseCouponResponse(UseCouponResult coupon) {
        this(
                coupon.id(),
                coupon.serialNumber(),
                coupon.type(),
                coupon.discountValue(),
                coupon.usedAt(),
                coupon.expiredAt()
        );
    }
}
