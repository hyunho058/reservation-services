package com.gd.reservationservices.presentation.performance;

import com.gd.reservationservices.application.performance.exception.PlaceService;
import com.gd.reservationservices.common.response.EmptyResponse;
import com.gd.reservationservices.presentation.performance.request.CreatePlaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("places")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public EmptyResponse createPlace(@RequestBody CreatePlaceRequest createPlaceRequest) {
        placeService.createPlace(createPlaceRequest.toVValue());

        return new EmptyResponse.Ok<>();
    }
}
