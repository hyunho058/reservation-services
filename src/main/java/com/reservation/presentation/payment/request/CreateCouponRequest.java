package com.reservation.presentation.payment.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.application.payment.dto.CreateCouponValue;
import com.reservation.domain.payment.Coupon;

import java.time.LocalDateTime;

public record CreateCouponRequest(
        Long performanceId,
        Coupon.Type type,
        Integer discountValue,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime expiredAt,
        Integer amount) {

    public CreateCouponValue toValue() {
        return new CreateCouponValue(
                this.performanceId(),
                this.type,
                this.discountValue(),
                this.expiredAt(),
                this.amount()
        );
    }

}
