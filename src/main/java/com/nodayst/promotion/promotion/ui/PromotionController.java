package com.nodayst.promotion.promotion.ui;

import com.nodayst.promotion.common.dto.ResponseMessage;
import com.nodayst.promotion.promotion.application.DeletePromotionService;
import com.nodayst.promotion.promotion.application.RegisterPromotionService;
import com.nodayst.promotion.promotion.ui.dto.RegisterPromotionRequest;
import com.nodayst.promotion.common.dto.ResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotion")
public class PromotionController {

    private final DeletePromotionService deletePromotionService;
    private final RegisterPromotionService registerPromotionService;

    @PostMapping
    public ResponseEntity registerPromotion(
        @Valid @RequestBody RegisterPromotionRequest registerPromotionRequestDto) {
        registerPromotionService.registerPromotion(registerPromotionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseDto(ResponseDto.SUCCESS, ResponseMessage.SUCCESS_MESSAGE));
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity deletePromotion(@PathVariable long promotionId) {
        deletePromotionService.deletePromotion(promotionId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(ResponseDto.SUCCESS, ResponseMessage.SUCCESS_MESSAGE));
    }
}