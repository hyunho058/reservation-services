package com.reservation.docs.performance;

import com.reservation.application.performance.PerformanceService;
import com.reservation.application.performance.dto.CreatePerformanceResult;
import com.reservation.application.performance.dto.CreatePerformanceValue;
import com.reservation.application.performance.dto.PerformancePlace;
import com.reservation.docs.RestDocsSupport;
import com.reservation.presentation.performance.PerformanceController;
import com.reservation.presentation.performance.request.CreatePerformanceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class PerformanceControllerDocsTest extends RestDocsSupport {
    private final PerformanceService performanceService = mock(PerformanceService.class);

    @Override
    protected Object initController() {
        return new PerformanceController(performanceService);
    }

    @DisplayName("공연 등록 API")
    @Test
    void create() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 20, 14, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 20, 16, 30, 0);

        LocalDateTime bookingStartDate = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime bookingEndDate = LocalDateTime.of(2023, 8, 19, 23, 59, 59);
        CreatePerformanceRequest request = new CreatePerformanceRequest(
                1L,
                "CLASSIC",
                startTime,
                endTime,
                bookingStartDate,
                bookingEndDate,
                "공연 이름",
                "공연 내용",
                "출연진",
                "ALL",
                50000,
                List.of(
                        new CreatePerformanceRequest.SeatRequest("A", 100),
                        new CreatePerformanceRequest.SeatRequest("B", 100),
                        new CreatePerformanceRequest.SeatRequest("C", 100)
                )
        );

        LocalDateTime createdAt = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        given(performanceService.create(any(CreatePerformanceValue.class)))
                .willReturn(new CreatePerformanceResult(
                        new PerformancePlace("공연장 이름", "서울시 강남구 역삼동"),
                        startTime,
                        endTime,
                        bookingStartDate,
                        bookingEndDate,
                        "CLASSIC",
                        "공연 이름",
                        "공연 내용",
                        "출연진",
                        "ALL",
                        createdAt,
                        updatedAt
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/performance")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("performance-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("placeId").type(JsonFieldType.NUMBER)
                                        .description("공연장 ID"),
                                fieldWithPath("category").type(JsonFieldType.STRING)
                                        .description("공연 카테고리"),
                                fieldWithPath("startAt").type(JsonFieldType.STRING)
                                        .description("공연 시작시간"),
                                fieldWithPath("endAt").type(JsonFieldType.STRING)
                                        .description("공연 종료시간"),
                                fieldWithPath("startReservationAt").type(JsonFieldType.STRING)
                                        .description("예매 시작시간"),
                                fieldWithPath("endReservationAt").type(JsonFieldType.STRING)
                                        .description("예매 종료시간"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("내용"),
                                fieldWithPath("acting").type(JsonFieldType.STRING)
                                        .description("출연진"),
                                fieldWithPath("filmRating").type(JsonFieldType.STRING)
                                        .description("관람 가능 연령"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("가격"),
                                fieldWithPath("seats").type(JsonFieldType.ARRAY)
                                        .description("등록 좌석 정보"),
                                fieldWithPath("seats.[].location").type(JsonFieldType.STRING)
                                        .description("등록 좌석 정보"),
                                fieldWithPath("seats.[].seatCount").type(JsonFieldType.NUMBER)
                                        .description("등록 좌석 정보")
                        ),
                        responseFields(
                                fieldWithPath("meta").type(JsonFieldType.OBJECT)
                                        .description("응답 상태"),
                                fieldWithPath("meta.result").type(JsonFieldType.STRING)
                                        .description("결과"),
                                fieldWithPath("meta.errorCode").type(JsonFieldType.STRING)
                                        .description("에러 코드")
                                        .optional(),
                                fieldWithPath("meta.errorMessage").type(JsonFieldType.STRING)
                                        .description("에러 메세지")
                                        .optional(),
                                fieldWithPath("content").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("content.place").type(JsonFieldType.OBJECT)
                                        .description("공연장 정보"),
                                fieldWithPath("content.place.name").type(JsonFieldType.STRING)
                                        .description("공연장 이름"),
                                fieldWithPath("content.place.location").type(JsonFieldType.STRING)
                                        .description("공연장 주소"),
                                fieldWithPath("content.category").type(JsonFieldType.STRING)
                                        .description("카테고리"),
                                fieldWithPath("content.startAt").type(JsonFieldType.STRING)
                                        .description("시작시간"),
                                fieldWithPath("content.startReservationAt").type(JsonFieldType.ARRAY)
                                        .description("예매 시작시간"),
                                fieldWithPath("content.endReservationAt").type(JsonFieldType.ARRAY)
                                        .description("예매 종료시간"),
                                fieldWithPath("content.createdAt").type(JsonFieldType.ARRAY)
                                        .description("등록 시간"),
                                fieldWithPath("content.updatedAt").type(JsonFieldType.ARRAY)
                                        .description("수정 시간"),
                                fieldWithPath("content.title").type(JsonFieldType.STRING)
                                        .description("제목"),
                                fieldWithPath("content.content").type(JsonFieldType.STRING)
                                        .description("내용"),
                                fieldWithPath("content.acting").type(JsonFieldType.STRING)
                                        .description("출연진"),
                                fieldWithPath("content.filmRating").type(JsonFieldType.STRING)
                                        .description("관람 가능 연령")
                        )
                ));
    }
}
