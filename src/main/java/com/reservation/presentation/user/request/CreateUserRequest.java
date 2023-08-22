package com.reservation.presentation.user.request;

import com.reservation.application.user.command.CreateUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateUserRequest(
    @NotBlank(message = "상품 이름은 필수입니다.") String userId,
    @NotBlank(message = "비밀번호는 필수입니다.")String userPw,
    @NotBlank(message = "이름은 필수입니다.")String name,
    @Positive(message = "나이는 양수입니다.")int age,
    @NotBlank(message = "메일은 필수입니다.")String email,
    @NotBlank(message = "전화번호는 필수입니다.")String phone,
    @NotBlank(message = "가입 유형은 필수입니다.")String role
) {
    public CreateUser toValue() {
        return new CreateUser(
            this.userId,
            this.userPw,
            this.name,
            this.age,
            this.email,
            this.phone,
            this.role
        );
    }
}
