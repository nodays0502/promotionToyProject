package com.nodayst.promotion.user.ui;

import com.nodayst.promotion.common.dto.ResponseMessage;
import com.nodayst.promotion.user.application.RegisterUserService;
import com.nodayst.promotion.user.application.ResignUserService;
import com.nodayst.promotion.user.ui.dto.RegisterUserRequest;
import com.nodayst.promotion.common.dto.ResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final RegisterUserService registerUserService;
    private final ResignUserService resignUserService;

    @PostMapping
    public ResponseEntity registerUser(
        @Valid @RequestBody RegisterUserRequest registerUserRequestDto) {
        registerUserService.register(registerUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseDto(ResponseDto.SUCCESS, ResponseMessage.SUCCESS_MESSAGE));
    }

    @PostMapping("/{userId}/resign")
    public ResponseEntity resignUser(@PathVariable long userId) {
        resignUserService.resign(userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(ResponseDto.SUCCESS, ResponseMessage.SUCCESS_MESSAGE));
    }
}

