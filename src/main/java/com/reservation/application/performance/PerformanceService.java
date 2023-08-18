package com.reservation.application.performance;

import com.reservation.application.performance.dto.CreatePerformanceResult;
import com.reservation.application.performance.dto.CreatePerformanceValue;
import com.reservation.application.performance.dto.PerformancePlace;
import com.reservation.application.performance.dto.SearchPerformanceResult;
import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.performance.Performance;
import com.reservation.domain.performance.PerformanceSeatGroups;
import com.reservation.domain.performance.Place;
import com.reservation.domain.performance.repository.PerformanceJdbcRepository;
import com.reservation.domain.performance.repository.PerformanceRepository;
import com.reservation.domain.performance.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final PlaceRepository placeRepository;
    private final PerformanceJdbcRepository performanceJdbcRepository;

    @Transactional
    public CreatePerformanceResult create(CreatePerformanceValue createPerformanceValue) {
        if (performanceRepository.exists(
            createPerformanceValue.placeId(),
            createPerformanceValue.startAt(),
            createPerformanceValue.endAt())
        ) {
            throw new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_AVAILABLE_DATETIME.name());
        }

        Place place = placeRepository.findById(createPerformanceValue.placeId())
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PLACE_NOT_FOUND.name()));

        PerformanceSeatGroups performanceSeats =
            new PerformanceSeatGroups(
                createPerformanceValue.seats().stream()
                    .map(s -> new PerformanceSeatGroups.SeatInfo(s.location(), s.seatCount()))
                    .collect(Collectors.toList())
            );

        if (!performanceSeats.seatRegistrationAvailable(place.getMaxSeat())) {
            throw new IllegalArgumentException(ErrorCode.PERFORMANCE_EXCEED_MAX_SEAT_ON_PLACE.name());
        }

        Performance newPerformance = createPerformanceValue.toEntity(place);
        performanceRepository.save(newPerformance);

        performanceJdbcRepository.saveAll(
            performanceSeats.getSeats(newPerformance.getId())
        );

        return new CreatePerformanceResult(
            newPerformance,
            new PerformancePlace(place)
        );
    }

    public SearchPerformanceResult searchBy(Long id) {
        Performance performance = performanceRepository.findPerformanceAndPlace(id)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PERFORMANCE_NOT_FOUND.name()));

        return new SearchPerformanceResult(
            performance,
            new PerformancePlace(performance.getPlace())
        );
    }
}
