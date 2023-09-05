package com.reservation.application.performance;

import com.reservation.application.performance.dto.CreateReservationResult;
import com.reservation.application.performance.dto.CreateReservationValue;
import com.reservation.application.performance.dto.SearchReservationListResult;
import com.reservation.application.performance.dto.SearchReservationResult;
import com.reservation.application.performance.value.LockKey;
import com.reservation.common.aop.DistributedLock;
import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Reservation;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.performance.repository.LockRepository;
import com.reservation.domain.performance.repository.PerformanceRepository;
import com.reservation.domain.performance.repository.ReservationRepository;
import com.reservation.domain.performance.repository.SeatRepository;
import com.reservation.domain.user.User;
import com.reservation.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final LockRepository lockRepository;
    private final ApplicationEventPublisher eventPublisher;

    @DistributedLock()
    public CreateReservationResult create(Long performanceId, CreateReservationValue requestValue) {
        LockKey lockKey =
            new LockKey(
                performanceId,
                requestValue.seatLocation(),
                requestValue.seatNumber()
            );

        System.out.println("create reservation -------------------");

        Performance performance = performanceRepository.findById(performanceId)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        Seat seat = seatRepository.findByPerformanceIdAndLocationAndNumber(
            performanceId,
            requestValue.seatLocation(),
            requestValue.seatNumber()
        ).orElseThrow(() -> new IllegalArgumentException(ErrorCode.SEAT_NOT_FOUND.name()));

        if (seat.isReserved()) {
            throw new IllegalArgumentException(ErrorCode.ALREADY_RESERVED_SEAT.name());
        }

        User user = userRepository.findById(requestValue.userId())
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.name()));

        Reservation reservation = reservationRepository.save(
            new Reservation(
                user,
                performance,
                seat,
                LocalDateTime.now()
            )
        );

        seat.reserve();

        return new CreateReservationResult(reservation);
    }

    @Transactional(readOnly = true)
    public SearchReservationListResult searchAllBy(Long performanceId, Pageable pageable) {
        Performance performance = performanceRepository.findById(performanceId)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        return new SearchReservationListResult(
            reservationRepository.findAllByPerformance(performance, pageable)
        );
    }

    @Transactional(readOnly = true)
    public SearchReservationResult searchBy(Long performanceId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.RESERVATION_NOT_FOUND.name()));

        if (!performanceId.equals(reservation.getPerformance().getId())) {
            throw new IllegalArgumentException(ErrorCode.RESERVATION_NOT_MATCHED_PERFORMANCE.name());
        }

        return new SearchReservationResult(reservation);
    }
}
