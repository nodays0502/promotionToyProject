package com.nodayst.promotion.promotion.application;

import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductRepository;
import com.nodayst.promotion.promotion.ui.dto.RegisterPromotionRequest;
import com.nodayst.promotion.exception.product.NotFoundProductException;
import com.nodayst.promotion.promotion.domain.Period;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterPromotionService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public void registerPromotion(RegisterPromotionRequest registerPromotionRequestDto) {
        long productId = registerPromotionRequestDto.getProductId();
        Period period = new Period(registerPromotionRequestDto.getStartDateTime(),
            registerPromotionRequestDto.getEndDateTime());
        Promotion promotion = Promotion.withOutId(period, registerPromotionRequestDto.getType(),
            registerPromotionRequestDto.getValue(),productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new NotFoundProductException();
        });
        promotion.registerProduct(product);
        promotionRepository.save(promotion);
    }
}
