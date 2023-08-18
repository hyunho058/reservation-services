package com.reservation.presentation.user;

import com.reservation.application.user.UserService;
import com.reservation.application.user.dto.SearchUserResult;
import com.reservation.application.user.dto.UpdateUserResult;
import com.reservation.common.response.EmptyResponse;
import com.reservation.common.response.SingleResponse;
import com.reservation.presentation.user.reqeust.UpdateUserRequest;
import com.reservation.presentation.user.request.CreateUserRequest;
import com.reservation.presentation.user.response.SearchUserResponse;
import com.reservation.presentation.user.response.UpdateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public EmptyResponse join(@RequestBody CreateUserRequest createUserRequest) {
        userService.joinBusinessUser(createUserRequest.toValue());

        return new EmptyResponse.Ok<>();
    }

    @GetMapping("/{id}")
    public SingleResponse<SearchUserResponse> searchBy(@PathVariable Long id) {
        SearchUserResult searchUserResult = userService.searchBy(id);

        SearchUserResponse searchUserResponse = new SearchUserResponse(searchUserResult);

        return new SingleResponse.Ok<>(searchUserResponse);
    }

    @PutMapping("/{id}")
    public SingleResponse<UpdateUserResponse> update(@PathVariable Long id,
                                                     @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResult updateUserResult = userService.update(id, updateUserRequest.toValue());

        return new SingleResponse.Ok<>(
            new UpdateUserResponse(updateUserResult)
        );
    }
}
