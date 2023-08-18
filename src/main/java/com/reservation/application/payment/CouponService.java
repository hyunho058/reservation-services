package com.reservation.application.payment;

import com.reservation.application.payment.command.CreateCouponValue;
import com.reservation.application.payment.command.UpdateCouponValue;
import com.reservation.application.payment.dto.CreateCouponResult;
import com.reservation.application.payment.dto.SearchCouponResult;
import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.payment.Coupon;
import com.reservation.domain.payment.repository.CouponRepository;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public List<CreateCouponResult> create(CreateCouponValue command) {
        Performance performance = performanceRepository.findById(command.performanceId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        List<Coupon> coupons = new ArrayList<>();

        for (int i = 0; i < command.amount(); i++) {
            Coupon coupon = new Coupon(
                    performance,
                    command.type(),
                    command.value(),
                    command.expiredAt());
            if (coupon.isOverPrice(command.value())) {
                throw new IllegalArgumentException(ErrorCode.COUPON_DISCOUNT_NOT_AVAILABLE_EXCEED_PERFORMANCE_PRICE.name());
            }
            coupons.add(coupon);
        }
        return couponRepository.saveAll(coupons)
                .stream().map(CreateCouponResult::new)
                .collect(Collectors.toList());

    }

    public SearchCouponResult search(Long id) {
        Coupon result = couponRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));
        return new SearchCouponResult(result);

    }

    @Transactional
    public void update(UpdateCouponValue value) {
        Coupon coupon = couponRepository.findById(value.id()).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        Performance performance = performanceRepository.findById(value.performanceId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        couponRepository.save(coupon.update(performance, value.type(), value.value(), value.expiredAt()));
    }

    @Transactional
    public void use(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        couponRepository.save(coupon.use());
    }

    @Transactional
    public void delete(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));
        couponRepository.save(coupon.delete());
    }
}
