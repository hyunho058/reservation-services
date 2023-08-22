package com.reservation;

import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.performance.repository.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServicesApplicationTests extends IntegrationTestSupport{
    @Autowired
    private SeatRepository seatRepository;
    
    @DisplayName("좌석 정보를 가져온다.")
    @Test
    void findByPerformanceIdAndLocationAndNumber() {
        //given
        Seat newSeat = new Seat(1L, "A", 1);
        seatRepository.save(newSeat);
        
        //when
        Seat seat = seatRepository.findByPerformanceIdAndLocationAndNumber(1L, "A", 1)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.SEAT_NOT_FOUND.name()));

        //then
        assertThat(seat)
                .extracting("performanceId", "location", "number", "isReserved")
                .contains(1L, "A", 1, false);
    }
}
