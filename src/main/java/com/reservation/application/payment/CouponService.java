package com.reservation.application.payment;

import com.reservation.application.payment.dto.CreateCouponValue;
import com.reservation.application.payment.dto.UpdateCouponValue;
import com.reservation.application.payment.dto.CreateCouponResult;
import com.reservation.application.payment.dto.SearchCouponResult;
import com.reservation.application.payment.dto.UpdateCouponResult;
import com.reservation.application.payment.dto.UseCouponResult;
import com.reservation.application.payment.value.SerialNumberGenerator;
import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.payment.Coupon;
import com.reservation.domain.payment.repository.CouponRepository;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public List<CreateCouponResult> create(CreateCouponValue value) {
        Performance performance = performanceRepository.findById(value.performanceId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        if (discountTypePolicy(value.type(), value.discountValue(), performance.getPrice())){
            throw new IllegalArgumentException(ErrorCode.COUPON_DISCOUNT_NOT_AVAILABLE_EXCEED_PERFORMANCE_PRICE.name());
        }

        SerialNumberGenerator serialNumberGenerator = new SerialNumberGenerator(value.amount());
        List<Coupon> coupons = getCoupons(value, performance, serialNumberGenerator.getList());

        return couponRepository.saveAll(coupons)
                .stream().map(CreateCouponResult::new)
                .collect(Collectors.toList());
    }

    public SearchCouponResult search(Long id) {
        Coupon result = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        return new SearchCouponResult(result);

    }

    public boolean checkkk(Long id){
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        Integer discountValue = coupon.getDiscountValue();
        Integer result = discountValue - 100;

        if (coupon.getUsedAt() != null) {
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public UpdateCouponResult update(UpdateCouponValue value) {
        Coupon coupon = couponRepository.findById(value.id())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        Performance performance = performanceRepository.findById(value.performanceId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        Coupon updatedCoupon = couponRepository.save(
                coupon.update(
                        performance,
                        value.type(),
                        value.value(),
                        value.expiredAt()
                ));

        return new UpdateCouponResult(updatedCoupon);
    }

    @Transactional
    public UseCouponResult use(Long id, LocalDateTime useTime) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        Coupon usedCoupon = couponRepository.save(
                coupon.use(useTime)
        );

        return new UseCouponResult(usedCoupon);
    }

    @Transactional
    public void delete(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COUPON_NOT_FOUND.name()));

        couponRepository.save(coupon.delete());
    }

    private boolean discountTypePolicy(Coupon.Type type, Integer value, Integer price) {
        if (Coupon.Type.PERCENT.equals(type)) {
            return value > 100;
        }
        if (Coupon.Type.WON.equals(type)) {
            return value > price;
        }

        return false;
    }

    private List<Coupon> getCoupons(CreateCouponValue command,
                                    Performance performance,
                                    List<String> serialNumbers) {

        return serialNumbers.stream()
                .map(serialNumber -> new Coupon(
                        performance,
                        serialNumber,
                        command.type(),
                        command.discountValue(),
                        command.expiredAt()
                )).toList();
    }
}
