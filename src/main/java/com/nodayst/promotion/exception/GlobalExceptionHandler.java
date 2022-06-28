package com.nodayst.promotion.exception;

import com.nodayst.promotion.common.dto.ResponseMessage;
import com.nodayst.promotion.exception.user.AlreadyUserStateIsResignException;
import com.nodayst.promotion.common.dto.ResponseDto;
import com.nodayst.promotion.exception.product.NotFoundProductException;
import com.nodayst.promotion.exception.promotion.NotFoundPromotionException;
import com.nodayst.promotion.exception.user.NotFoundUserException;
import com.nodayst.promotion.exception.user.ResignedUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.BAD_REQUEST_MESSAGE));
    }

    @ExceptionHandler(AlreadyUserStateIsResignException.class)
    public ResponseEntity handleAlreadyUserStateIsResignException(
        AlreadyUserStateIsResignException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.ALREADY_RESIGNED_MESSAGE));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity handleNotFoundUserException(
        NotFoundUserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.NOT_FOUND_USER_MESSAGE));
    }

    @ExceptionHandler(ResignedUserException.class)
    public ResponseEntity handleResignedUserException(
        ResignedUserException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.UNAUTHORIZED_MESSAGE));
    }

    @ExceptionHandler(NotFoundPromotionException.class)
    public ResponseEntity handleNotFoundPromotionException(
        NotFoundPromotionException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.NOT_FOUND_PROMOTION_MESSAGE));
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity handleNotFoundProductException(
        NotFoundProductException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseDto(ResponseDto.FAIL, ResponseMessage.NOT_FOUND_PRODUCT_MESSAGE));
    }
}
