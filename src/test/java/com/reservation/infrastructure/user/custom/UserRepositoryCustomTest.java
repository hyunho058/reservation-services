package com.reservation.infrastructure.user.custom;

import com.reservation.IntegrationTestSupport;
import com.reservation.domain.user.Role;
import com.reservation.domain.user.User;
import com.reservation.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRepositoryCustomTest extends IntegrationTestSupport {
    @Autowired
    private UserRepository userRepository;

    @DisplayName("이미 등록된 아이디가 있는지 확인한다.")
    @Test
    void existsUserId() {
        //given
        User user = new User(
                "user01",
                "user1234",
                "user01",
                22,
                "user01@test.com",
                "01012345678",
                Role.CUSTOMER
        );
        userRepository.save(user);
        String newUserId = "user02";

        //when
        Boolean exists = userRepository.exists(newUserId);

        //then
        assertThat(exists).isFalse();
    }

    @DisplayName("이미 등록된 아이디가 있으면 TRUE를 반호나한다.")
    @Test
    void existsUserIdWithDuplicate() {
        //given
        User user = new User(
                "user01",
                "user1234",
                "user01",
                22,
                "user01@test.com",
                "01012345678",
                Role.CUSTOMER
        );
        userRepository.save(user);
        String duplicateUserId = "user01";

        //when
        Boolean exists = userRepository.exists(duplicateUserId);

        //then
        assertThat(exists).isTrue();
    }
}
