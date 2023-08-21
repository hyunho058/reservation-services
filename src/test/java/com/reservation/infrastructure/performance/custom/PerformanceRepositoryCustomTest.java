package com.reservation.infrastructure.performance.custom;

import com.reservation.IntegrationTestSupport;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Place;
import com.reservation.domain.performance.repository.PerformanceRepository;
import com.reservation.domain.performance.repository.PlaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceRepositoryCustomTest extends IntegrationTestSupport {
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @DisplayName("공연 정보 등록 시 선택한 공연장에 공연 등록이 가능한지 확인")
    @Test
    void existsPerformance() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);
        Place newPlace = new Place("공연장 이름", "공연장 주소", 500);
        Place savedPlace = placeRepository.save(newPlace);

        Performance newPerformance = new Performance(
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
        performanceRepository.save(newPerformance);

        //when
        boolean existsPerformance = performanceRepository.exists(savedPlace.getId(), startTime, endTime);

        //then
        assertThat(existsPerformance).isTrue();
    }

    @DisplayName("등록 하려는 공연 종료시간에 공연 이 등록되어있다.")
    @Test
    void existsAlreadyPerformance() {
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
        performanceRepository.save(performance);

        LocalDateTime newPerformanceStartTime = LocalDateTime.of(2023, 8, 20, 12, 0, 0);
        LocalDateTime newPerformanceEndTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);

        //when
        boolean existsPerformance =
                performanceRepository.exists(savedPlace.getId(), newPerformanceStartTime, newPerformanceEndTime);

        //then
        assertThat(existsPerformance).isTrue();
    }

    @DisplayName("등록 하려는 공연 종료시간에 공연 이 등록되어있다.")
    @Test
    void existsPerformanceAlreadyRegisteredAtTheStartTime() {
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
        performanceRepository.save(performance);

        LocalDateTime newPerformanceStartTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);
        LocalDateTime newPerformanceEndTime = LocalDateTime.of(2023, 8, 20, 18, 0, 0);

        //when
        boolean existsPerformance =
                performanceRepository.exists(savedPlace.getId(), newPerformanceStartTime, newPerformanceEndTime);

        //then
        assertThat(existsPerformance).isTrue();
    }

    @DisplayName("등록하려는 공연장에 공연 정보가 없다.")
    @Test
    void notExistsPerformance() {
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
        performanceRepository.save(performance);

        LocalDateTime newPerformanceStartTime = LocalDateTime.of(2023, 8, 20, 16, 31, 0);
        LocalDateTime newPerformanceEndTime = LocalDateTime.of(2023, 8, 20, 18, 0, 0);

        //when
        boolean existsPerformance =
                performanceRepository.exists(savedPlace.getId(), newPerformanceStartTime, newPerformanceEndTime);

        //then
        assertThat(existsPerformance).isFalse();
    }
}