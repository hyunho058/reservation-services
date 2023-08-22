package com.reservation.domain.performance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SeatTest {
    @DisplayName("좌석이 예매 되어 있지 않으면 FALSE 이다.")
    @Test
    void isNotReserved() {
        //given
        Seat seat = new Seat(1L, "A", 1);

        //when
        Boolean reserved = seat.isReserved();

        //then
        assertThat(reserved).isFalse();
    }

    @DisplayName("좌석이 예매 되어 있으면 TRUE 이다.")
    @Test
    void isReserved() {
        //given
        Seat seat = new Seat(1L, "A", 1, true);

        //when
        Boolean reserved = seat.isReserved();

        //then
        assertThat(reserved).isTrue();
    }

    @DisplayName("좌석 예약을 하면 좌석 예약 상태 정보가 FALSE에서 TRUE로 변경된다.")
    @Test
    void reserve() {
        //given
        Seat seat = new Seat(1L, "A", 1, false);

        //when
        seat.reserve();

        //then
        assertThat(seat.getIsReserved()).isTrue();
    }
}
