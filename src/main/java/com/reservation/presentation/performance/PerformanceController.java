package com.reservation.presentation.performance;

import com.reservation.application.performance.PerformanceService;
import com.reservation.application.performance.dto.CreatePerformanceResult;
import com.reservation.application.performance.dto.SearchPerformanceResult;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.performance.request.CreatePerformanceRequest;
import com.reservation.presentation.performance.response.CreatePerformanceResponse;
import com.reservation.presentation.performance.response.FindPerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    @PostMapping
    public SingleResponse<CreatePerformanceResponse> create(@RequestBody CreatePerformanceRequest createPerformanceRequest) {
        CreatePerformanceResult createPerformanceResult =
            performanceService.create(createPerformanceRequest.toValue());

        return new SingleResponse.Ok<>(
            new CreatePerformanceResponse(createPerformanceResult));
    }

    @GetMapping("/business/{id}")
    public SingleResponse<FindPerformanceResponse> searchBy(@PathVariable Long id) {
        SearchPerformanceResult searchPerformanceResult = performanceService.searchBy(id);

        return new SingleResponse.Ok<>(
            new FindPerformanceResponse(searchPerformanceResult)
        );
    }
}
