package com.reservation.presentation.payment;

import com.reservation.application.payment.CouponService;
import com.reservation.common.response.EmptyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/coupons")
@RequiredArgsConstructor
public class CustomerCouponController {
    private final CouponService couponService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmptyResponse useCoupon(@PathVariable Long id) {
        couponService.use(id);
        return new EmptyResponse.Ok<>();
    }
}
