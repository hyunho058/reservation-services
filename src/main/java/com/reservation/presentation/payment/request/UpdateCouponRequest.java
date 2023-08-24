package com.reservation.presentation.payment.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.application.payment.dto.UpdateCouponValue;
import com.reservation.domain.payment.Coupon;

import java.time.LocalDateTime;

public record UpdateCouponRequest(
        Long performanceId,
        Coupon.Type type,
        Integer value,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime expiredAt,
        Integer amount) {

    public UpdateCouponValue toValue(Long id) {
        return new UpdateCouponValue(
                id,
                this.performanceId(),
                this.type,
                this.value(),
                this.expiredAt()
        );
    }
}
