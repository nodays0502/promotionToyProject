package com.nodayst.promotion.user.ui.dto;

import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    private String name;
    @NotNull
    private UserType type;
    @NotNull
    private UserState state;

    public static User mapToUser(RegisterUserRequest registerUserRequestDto) {
        return User.withOutId(registerUserRequestDto.name,
            registerUserRequestDto.type,
            registerUserRequestDto.state.NORMAL);
    }
}
