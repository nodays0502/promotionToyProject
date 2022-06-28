package com.nodayst.promotion.product.ui;

import static com.nodayst.promotion.common.dto.ResponseMessage.SUCCESS_MESSAGE;

import com.nodayst.promotion.product.application.DeleteProductService;
import com.nodayst.promotion.product.application.GetProductService;
import com.nodayst.promotion.common.dto.ResponseDto;
import com.nodayst.promotion.common.dto.ResponseWithDataDto;
import com.nodayst.promotion.product.application.GetPromotionInfoService;
import com.nodayst.promotion.product.application.RegisterProductService;
import com.nodayst.promotion.product.ui.dto.GetPromotionInfoResponse;
import com.nodayst.promotion.product.ui.dto.ProductDto;
import com.nodayst.promotion.product.ui.dto.RegisterProductRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class productController {

    private final DeleteProductService deleteProductService;
    private final GetPromotionInfoService findPromotionPriceService;
    private final GetProductService getProductService;
    private final RegisterProductService registerProductService;

    @PostMapping
    public ResponseEntity registerProduct(
        @Valid @RequestBody RegisterProductRequest registerProductRequestDto) {
        registerProductService.registerProduct(registerProductRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ResponseDto(ResponseDto.SUCCESS, SUCCESS_MESSAGE));
    }

    @GetMapping("/{userId}")
    public ResponseEntity getProducts(@PathVariable long userId) {
        List<ProductDto> products = getProductService.getProduct(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseWithDataDto(ResponseDto.SUCCESS, SUCCESS_MESSAGE, products));
    }

    @GetMapping("/{productId}/promotionInfo")
    public ResponseEntity getPromotionInfo(@PathVariable long productId) {
        GetPromotionInfoResponse promotionPrice = findPromotionPriceService.findPromotionLowestPrice(
            productId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseWithDataDto(ResponseDto.SUCCESS, SUCCESS_MESSAGE, promotionPrice));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProducts(@PathVariable long productId) {
        deleteProductService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(ResponseDto.SUCCESS, SUCCESS_MESSAGE));
    }
}
