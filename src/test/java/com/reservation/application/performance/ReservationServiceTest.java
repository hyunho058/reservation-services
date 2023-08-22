package com.reservation.application.performance;

import com.reservation.IntegrationTestSupport;
import com.reservation.application.performance.dto.CreateReservationResult;
import com.reservation.application.performance.dto.CreateReservationValue;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.Place;
import com.reservation.domain.performance.Seat;
import com.reservation.domain.performance.repository.PerformanceRepository;
import com.reservation.domain.performance.repository.PlaceRepository;
import com.reservation.domain.performance.repository.ReservationRepository;
import com.reservation.domain.performance.repository.SeatRepository;
import com.reservation.domain.user.Role;
import com.reservation.domain.user.User;
import com.reservation.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest extends IntegrationTestSupport {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @DisplayName("공연 좌석예매 한다.")
    @Test
    void create() {
        //given
        Place newPlace = new Place("공연장 이름", "공연장 주소", 500);
        Place savedPlace = placeRepository.save(newPlace);

        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);

        Performance newPerformance = new Performance(
                savedPlace,
                Performance.Category.SPORT,
                startTime,
                endTime,
                bookingStartDate,
                bookingEndDate,
                "공연 이름",
                "공연 내용",
                "출연진",
                Performance.FilmRating.TWELVE,
                50000,
                null,
                null
        );
        Performance savedPerformance = performanceRepository.save(newPerformance);
        Long performanceId = savedPerformance.getId();

        Seat newSeat = new Seat(performanceId, "A", 1);
        seatRepository.save(newSeat);

        User user = new User(
                "user01",
                "user1234",
                "user01",
                22,
                "user01@test.com",
                "01012345678",
                Role.CUSTOMER
        );
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        CreateReservationValue request =
                new CreateReservationValue(userId, "A", 1);

        //when
        CreateReservationResult createReservationResult =
                reservationService.create(performanceId, request);

        //then
        assertThat(createReservationResult.user())
                .extracting("userId", "userPw", "name", "age", "email", "phone", "role")
                .contains("user01", "user1234", "user01", 22, "user01@test.com", "01012345678", Role.CUSTOMER);

        assertThat(createReservationResult.seat())
                .extracting("performanceId", "location", "number", "isReserved")
                .contains(performanceId, "A", 1, true);

        assertThat(createReservationResult.performance())
                .extracting("category", "startAt", "startReservationAt", "endReservationAt", "title", "content", "acting", "filmRating")
                .contains(Performance.Category.SPORT, startTime, bookingStartDate, bookingEndDate, "공연 이름", "공연 내용", "출연진", Performance.FilmRating.TWELVE);
    }

    @DisplayName("공연 좌석예매 한다.")
    @Test
    void createWithDuplicate() {
        //given
        Place newPlace = new Place("공연장 이름", "공연장 주소", 500);
        Place savedPlace = placeRepository.save(newPlace);

        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);

        Performance newPerformance = new Performance(
                savedPlace,
                Performance.Category.SPORT,
                startTime,
                endTime,
                bookingStartDate,
                bookingEndDate,
                "공연 이름",
                "공연 내용",
                "출연진",
                Performance.FilmRating.TWELVE,
                50000,
                null,
                null
        );
        Performance savedPerformance = performanceRepository.save(newPerformance);
        Long performanceId = savedPerformance.getId();

        Seat newSeat = new Seat(performanceId, "A", 1);
        seatRepository.save(newSeat);

        User user1 = new User(
                "user01",
                "user1234",
                "user01",
                22,
                "user01@test.com",
                "01012345678",
                Role.CUSTOMER
        );
        User savedUser1 = userRepository.save(user1);

        User user2= new User(
                "user01",
                "user1234",
                "user01",
                22,
                "user01@test.com",
                "01012345678",
                Role.CUSTOMER
        );
        User savedUser2 = userRepository.save(user2);

        CreateReservationValue request1 =
                new CreateReservationValue(savedUser1.getId(), "A", 1);
        CreateReservationValue request2 =
                new CreateReservationValue(savedUser2.getId(), "A", 1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        //when
        CreateReservationResult createReservationResult =
                reservationService.create(performanceId, request1);


        //then
        assertThat(createReservationResult.user())
                .extracting("userId", "userPw", "name", "age", "email", "phone", "role")
                .contains("user01", "user1234", "user01", 22, "user01@test.com", "01012345678", Role.CUSTOMER);

        assertThat(createReservationResult.seat())
                .extracting("performanceId", "location", "number", "isReserved")
                .contains(performanceId, "A", 1, true);

        assertThat(createReservationResult.performance())
                .extracting("category", "startAt", "startReservationAt", "endReservationAt", "title", "content", "acting", "filmRating")
                .contains(Performance.Category.SPORT, startTime, bookingStartDate, bookingEndDate, "공연 이름", "공연 내용", "출연진", Performance.FilmRating.TWELVE);
    }
}
