package com.reservation.presentation.user;

import com.reservation.application.user.command.CreateUser;
import com.reservation.application.user.dto.SavedUserValue;
import com.reservation.application.user.dto.SearchUserResult;
import com.reservation.application.user.dto.UpdateUserResult;
import com.reservation.application.user.dto.UpdateUserValue;
import com.reservation.infrastructure.user.value.Role;
import com.reservation.presentation.ControllerTestSupport;
import com.reservation.presentation.user.request.CreateUserRequest;
import com.reservation.presentation.user.request.UpdateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName("신규 유저를 등록한다.")
    @Test
    void join() throws Exception {
        //given
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

        //when
        //then
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("유저를 조회한다.")
    @Test
    void searchBy() throws Exception {
        //given
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

        //when
        //then
        mockMvc.perform(
                        get("/users/{id}", id)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("사용자가 개인 정보를 수정한다.")
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
                        MockMvcRequestBuilders.put("/users/{id}", userId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
