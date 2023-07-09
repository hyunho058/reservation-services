package com.gd.reservationservices.application.performance.exception;

import com.gd.reservationservices.application.performance.dto.CreatePlaceValue;
import com.gd.reservationservices.infrastructure.performance.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public void create(CreatePlaceValue createPlaceValue) {
        if (placeRepository.existsByLocation(createPlaceValue.location())){
            throw new IllegalArgumentException("이미 등록 되어있는 공영장 입니다.");
        }

        placeRepository.save(createPlaceValue.toEntity());
    }
}
