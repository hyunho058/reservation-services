package com.reservation.domain.performance;

import com.reservation.application.performance.dto.CreatePerformanceValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceSeatGroupsTest {
    @DisplayName("공연 등록 정보 좌석수가 공연장 좌석수보다 큰 경우")
    @Test
    void seatRegistrationIsNotPossible() {
        //given

        List<CreatePerformanceValue.SeatValue> seats =
                List.of(
                        new CreatePerformanceValue.SeatValue("A", 50),
                        new CreatePerformanceValue.SeatValue("B", 50)
                );

        PerformanceSeatGroups performanceSeats =
                new PerformanceSeatGroups(
                        seats.stream()
                                .map(s -> new PerformanceSeatGroups.SeatInfo(s.location(), s.seatCount()))
                                .collect(Collectors.toList())
                );
        int maxSeats = 99;

        //when
        boolean seatRegistrationIsNotPossible = performanceSeats.seatRegistrationNotPossible(maxSeats);

        //then
        assertThat(seatRegistrationIsNotPossible).isTrue();
    }

    @DisplayName("공연 등록 정보 좌석수가 공연장 좌석수보다 작거나 같은 경우")
    @Test
    void seatRegistrationIsPossible() {
        //given
        List<CreatePerformanceValue.SeatValue> seats =
                List.of(
                        new CreatePerformanceValue.SeatValue("A", 50),
                        new CreatePerformanceValue.SeatValue("B", 50)
                );

        PerformanceSeatGroups performanceSeats =
                new PerformanceSeatGroups(
                        seats.stream()
                                .map(s -> new PerformanceSeatGroups.SeatInfo(s.location(), s.seatCount()))
                                .collect(Collectors.toList())
                );
        int maxSeats1 = 101;
        int maxSeats2 = 100;

        //when
        boolean seatRegistrationIsNotPossible1 = performanceSeats.seatRegistrationNotPossible(maxSeats1);
        boolean seatRegistrationIsNotPossible2 = performanceSeats.seatRegistrationNotPossible(maxSeats2);

        //then
        assertThat(seatRegistrationIsNotPossible1).isFalse();
        assertThat(seatRegistrationIsNotPossible2).isFalse();
    }

    @DisplayName("공연 좌석 등록정보를 도메인 정보로 변환")
    @Test
    void getSeats() {
        //given
        List<CreatePerformanceValue.SeatValue> seatValues =
                List.of(
                        new CreatePerformanceValue.SeatValue("A", 50),
                        new CreatePerformanceValue.SeatValue("B", 50)
                );

        PerformanceSeatGroups performanceSeats =
                new PerformanceSeatGroups(
                        seatValues.stream()
                                .map(s -> new PerformanceSeatGroups.SeatInfo(s.location(), s.seatCount()))
                                .collect(Collectors.toList())
                );

        Long performanceId = 1L;

        //when
        List<Seat> convertedSeats = performanceSeats.getSeats(performanceId);

        //then
        assertThat(convertedSeats).hasSize(100);
    }
}
