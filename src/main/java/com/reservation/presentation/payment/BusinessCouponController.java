package com.reservation.presentation.payment;

import com.reservation.application.payment.CouponService;
import com.reservation.common.response.EmptyResponse;
import com.reservation.common.response.ListResponse;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.payment.request.CreateCouponRequest;
import com.reservation.presentation.payment.request.UpdateCouponRequest;
import com.reservation.presentation.payment.response.CreateCouponResponse;
import com.reservation.presentation.payment.response.SearchCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/business/coupons")
@RequiredArgsConstructor
public class BusinessCouponController {
    private final CouponService couponService;

    //todo 따닥 이슈 고민해보기
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListResponse<CreateCouponResponse> create(@RequestBody CreateCouponRequest request) {
        List<CreateCouponResponse> result = couponService.create(request.toValue())
                .stream()
                .map(CreateCouponResponse::new)
                .collect(Collectors.toList());

        return new ListResponse.Ok<>(result, null);
    }

    //todo 추후에 id가 아닌 쿠폰번호로 조회하기
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResponse<SearchCouponResponse> search(@PathVariable Long id) {
        SearchCouponResponse result = new SearchCouponResponse(couponService.search(id));
        return new SingleResponse.Ok<>(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmptyResponse update(@PathVariable Long id,
                                @RequestBody UpdateCouponRequest request) {
        couponService.update(request.toValue(id));
        return new EmptyResponse.Ok<>();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmptyResponse delete(@PathVariable Long id) {
        couponService.delete(id);
        return new EmptyResponse.Ok<>();
    }

}
