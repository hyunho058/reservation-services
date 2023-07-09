package com.gd.reservationservices.application.performance;

import com.gd.reservationservices.application.performance.dto.ReservationCreateValue;
import com.gd.reservationservices.application.performance.exception.*;
import com.gd.reservationservices.application.user.exception.UserNotFoundException;
import com.gd.reservationservices.domain.performance.Performance;
import com.gd.reservationservices.domain.performance.Reservation;
import com.gd.reservationservices.domain.performance.Seat;
import com.gd.reservationservices.domain.user.User;
import com.gd.reservationservices.infrastructure.user.UserRepository;
import com.gd.reservationservices.infrastructure.performance.PerformanceRepository;
import com.gd.reservationservices.infrastructure.performance.ReservationRepository;
import com.gd.reservationservices.infrastructure.performance.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long performanceId, ReservationCreateValue requestValue) {
        Performance performance = performanceRepository.findById(performanceId)
            .orElseThrow(PerformanceNotFoundException::new);

        Seat seat = seatRepository.findByPerformanceIdAndLocationAndNumber(
            performanceId, requestValue.seatLocation(), requestValue.seatNumber()
        ).orElseThrow(SeatNotFoundException::new);

        if (seat.isReserved()) {
            throw new AlreadyReservedSeatException();
        }

        User user = userRepository.findById(requestValue.userId())
                .orElseThrow(UserNotFoundException::new);

        reservationRepository.save(
            new Reservation(
                user,
                performance,
                seat,
                LocalDateTime.now()
            )
        );

        seat.reserve();
    }

    public Page<Reservation> getAll(Long performanceId, Pageable pageable) {
        Performance performance = performanceRepository.findById(performanceId)
            .orElseThrow(PerformanceNotFoundException::new);

        return reservationRepository.findAllByPerformance(performance, pageable);
    }

    public Reservation getById(Long performanceId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFoundException::new);

        if (!performanceId.equals(reservation.getPerformance().getId())) {
            throw new ReservationNotMatchedException();
        }

        return reservation;
    }
}
