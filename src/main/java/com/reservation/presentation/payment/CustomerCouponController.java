package com.reservation.presentation.payment;

import com.reservation.application.payment.CouponService;
import com.reservation.application.payment.dto.UseCouponResult;
import com.reservation.common.response.EmptyResponse;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.payment.response.UseCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/customer/coupons")
@RequiredArgsConstructor
public class CustomerCouponController {
    private final CouponService couponService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResponse<UseCouponResponse> useCoupon(@PathVariable Long id) {
        UseCouponResult result = couponService.use(id, LocalDateTime.now());

        return new SingleResponse.Ok<>(
                new UseCouponResponse(result)
        );
    }
}
