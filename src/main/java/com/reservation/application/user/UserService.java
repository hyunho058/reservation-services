package com.reservation.application.user;

import com.reservation.application.user.command.CreateUser;
import com.reservation.application.user.dto.SavedUserValue;
import com.reservation.application.user.dto.SearchUserResult;
import com.reservation.application.user.dto.UpdateUserResult;
import com.reservation.application.user.dto.UpdateUserValue;
import com.reservation.common.exception.ErrorCode;
import com.reservation.domain.user.User;
import com.reservation.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SavedUserValue join(CreateUser createUser) {
        if (userRepository.exists(createUser.userId())) {
            throw new IllegalArgumentException(ErrorCode.ALREADY_REGISTERED_USER.name());
        }

        User savedUser = userRepository.save(createUser.toEntity());

        return new SavedUserValue(savedUser);
    }

    public SearchUserResult searchBy(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.name()));

        return new SearchUserResult(user);
    }

    @Transactional
    public UpdateUserResult update(Long userId, UpdateUserValue updateUserValue) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.name()));

        user.updateInformation(
            updateUserValue.userPw(),
            updateUserValue.name(),
            updateUserValue.age()
        );

        return new UpdateUserResult(user);
    }
    public double getRandomNumber() {
        return Math.random() * 10;
    }
}
