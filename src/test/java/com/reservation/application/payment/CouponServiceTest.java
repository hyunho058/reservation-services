package com.reservation.application.payment;

import com.reservation.IntegrationTestSupport;
import com.reservation.application.payment.command.CreateCouponValue;
import com.reservation.application.payment.dto.CreateCouponResult;
import com.reservation.domain.payment.Coupon;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Place;
import com.reservation.domain.performance.repository.PerformanceRepository;
import com.reservation.domain.performance.repository.PlaceRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CouponServiceTest extends IntegrationTestSupport {
    @Autowired
    private CouponService couponService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @DisplayName("")
    @Test
    void create() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);
        Place newPlace = new Place("공연장 이름", "공연장 주소", 500);
        Place savedPlace = placeRepository.save(newPlace);

        Performance performance = new Performance(
                savedPlace,
                Performance.Category.CLASSIC,
                startTime,
                endTime,
                bookingStartDate,
                bookingEndDate,
                "공연 이름",
                "공연 내용",
                "출연진",
                Performance.FilmRating.ALL,
                50000,
                null,
                null
        );
        Performance savedPerformance = performanceRepository.save(performance);
        Long performanceId = savedPerformance.getId();

        LocalDateTime expiredTime = LocalDateTime.of(2023, 8, 19, 23, 59, 59);
        CreateCouponValue createCouponValue = new CreateCouponValue(
                performanceId,
                Coupon.Type.PERCENT,
                10,
                expiredTime,
                10
        );

        //when
        List<CreateCouponResult> createCouponResults = couponService.create(createCouponValue);

        //then
//        assertThat(createCouponResults).hasSize(10)
//                .extracting("performanceTitle", "type", "value", "expiredAt")
//                .containsExactlyInAnyOrder(
//                        Tuple.tuple()
//                )
    }
}
