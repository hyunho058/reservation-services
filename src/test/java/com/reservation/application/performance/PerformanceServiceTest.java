package com.reservation.application.performance;

import com.reservation.IntegrationTestSupport;
import com.reservation.application.performance.dto.CreatePerformanceResult;
import com.reservation.application.performance.dto.CreatePerformanceValue;
import com.reservation.application.performance.dto.PerformancePlace;
import com.reservation.application.performance.dto.SearchPerformanceResult;
import com.reservation.domain.performance.Place;
import com.reservation.domain.performance.repository.PlaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class PerformanceServiceTest extends IntegrationTestSupport {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PlaceRepository placeRepository;

    @DisplayName("공연 정보를 등록한다.")
    @Test
    void create() {
        //given
        Place savedPlace = placeRepository.save(new Place("공연장", "공연장 주소", 300));

        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);

        CreatePerformanceValue createPerformanceValue = new CreatePerformanceValue(
                savedPlace.getId(),
                "CLASSIC",
                startTime,
                endTime,
                bookingStartDate,
                bookingEndDate,
                "공연 이름",
                "공연 내용",
                "출연진",
                "ALL",
                50000,
                List.of(
                        new CreatePerformanceValue.SeatValue("A", 100),
                        new CreatePerformanceValue.SeatValue("B", 100),
                        new CreatePerformanceValue.SeatValue("C", 100)
                )
        );

        //when
        CreatePerformanceResult createPerformanceResult = performanceService.create(createPerformanceValue);

        //then
        assertThat(createPerformanceResult)
                .extracting("place", "startAt", "endAt", "startReservationAt", "endReservationAt", "category", "title", "content", "acting", "filmRating")
                .contains(new PerformancePlace(savedPlace), startTime, endTime, bookingStartDate, bookingEndDate, "CLASSIC", "공연 이름", "공연 내용", "출연진", "ALL");
    }

    @DisplayName("공연 정보를 조회한다.")
    @Test
    void searchBy() {
        //given


        //when
        //then
    }
}
