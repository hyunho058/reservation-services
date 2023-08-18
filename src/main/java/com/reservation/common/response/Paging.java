package com.reservation.common.response;


public record Paging(Long totalCount, Integer currentPage, Integer perPage, Integer totalPage) {
}
