package com.reservation.docs.user;

import com.reservation.application.user.UserService;
import com.reservation.application.user.command.CreateUser;
import com.reservation.application.user.dto.SavedUserValue;
import com.reservation.application.user.dto.SearchUserResult;
import com.reservation.application.user.dto.UpdateUserResult;
import com.reservation.application.user.dto.UpdateUserValue;
import com.reservation.docs.RestDocsSupport;
import com.reservation.infrastructure.user.value.Role;
import com.reservation.presentation.user.UserController;
import com.reservation.presentation.user.request.CreateUserRequest;
import com.reservation.presentation.user.request.UpdateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserControllerDocsTest extends RestDocsSupport {
    private final UserService userService = Mockito.mock(UserService.class);

    @Override
    protected Object initController() {
        return new UserController(userService);
    }

    @DisplayName("유저 회원가입 API")
    @Test
    void join() throws Exception{
        CreateUserRequest request = new CreateUserRequest(
                "user01",
                "pw12345",
                "테스터",
                22,
                "user01@test.com",
                "01012345678",
                "CUSTOMER"
        );
        given(userService.join(any(CreateUser.class)))
                .willReturn(new SavedUserValue(
                        "user01",
                        "테스터",
                        22,
                        "user01@test.com",
                        "01012345678",
                        Role.valueOf("CUSTOMER")
                ));

        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("user-join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.STRING)
                                        .description("아이디"),
                                fieldWithPath("userPw").type(JsonFieldType.STRING)
                                        .description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER)
                                        .description("나이"),
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일 주소"),
                                fieldWithPath("phone").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("role").type(JsonFieldType.STRING)
                                        .description("회원 타입")
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
                                fieldWithPath("content.userId").type(JsonFieldType.STRING)
                                        .description("아이디"),
                                fieldWithPath("content.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("content.age").type(JsonFieldType.NUMBER)
                                        .description("나이"),
                                fieldWithPath("content.email").type(JsonFieldType.STRING)
                                        .description("이메일 주소"),
                                fieldWithPath("content.phone").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("content.role").type(JsonFieldType.STRING)
                                        .description("회원 타입")
                        )
                ));
    }

    @DisplayName("유저 조회 API")
    @Test
    void searchBy() throws Exception {
        Long id = 1L;
        given(userService.searchBy(any(Long.class)))
                .willReturn(new SearchUserResult(
                        "user01",
                        "테스터",
                        20,
                        "user01@test.com",
                        "01011111234",
                        Role.CUSTOMER
                ));

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/users/{id}", id)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("user-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("USER ID")
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
                                fieldWithPath("content.userId").type(JsonFieldType.STRING)
                                        .description("아이디"),
                                fieldWithPath("content.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("content.age").type(JsonFieldType.NUMBER)
                                        .description("나이"),
                                fieldWithPath("content.email").type(JsonFieldType.STRING)
                                        .description("이메일 주소"),
                                fieldWithPath("content.phone").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("content.role").type(JsonFieldType.STRING)
                                        .description("회원 타입")
                        )
                ));
    }

    @DisplayName("사용자 정보 수정 API")
    @Test
    void update() throws Exception {
        //given
        Long userId = 1L;
        UpdateUserRequest request = new UpdateUserRequest("test12345", "테스터", 22);

        given(userService.update(any(Long.class), any(UpdateUserValue.class)))
                .willReturn(new UpdateUserResult(
                        "user01",
                        "테스터",
                        22,
                        "uwer01@test.com",
                        "01012345678",
                        com.reservation.domain.user.Role.CUSTOMER
                ));

        //when
        //then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.put("/users/{id}", userId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("user-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("USER ID")
                        ),
                        requestFields(
                                fieldWithPath("userPw").type(JsonFieldType.STRING)
                                        .description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER)
                                        .description("나이")
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
                                fieldWithPath("content.userId").type(JsonFieldType.STRING)
                                        .description("아이디"),
                                fieldWithPath("content.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("content.age").type(JsonFieldType.NUMBER)
                                        .description("나이"),
                                fieldWithPath("content.email").type(JsonFieldType.STRING)
                                        .description("이메일 주소"),
                                fieldWithPath("content.phone").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("content.role").type(JsonFieldType.STRING)
                                        .description("회원 타입")
                        )
                ));
    }
}
