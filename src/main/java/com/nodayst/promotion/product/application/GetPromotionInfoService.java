package com.nodayst.promotion.product.application;

import com.nodayst.promotion.exception.product.NotFoundProductException;
import com.nodayst.promotion.product.domain.CalculateLowestPromotionPriceService;
import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductRepository;
import com.nodayst.promotion.product.ui.dto.GetPromotionInfoResponse;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPromotionInfoService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final CalculateLowestPromotionPriceService calculateLowestPromotionPriceService;

    public GetPromotionInfoResponse findPromotionLowestPrice(long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new NotFoundProductException();
        });

        List<Promotion> promotions = promotionRepository.findPromotionByProductIdAndPeriod(
            productId,
            LocalDateTime.now());

        Money lowestPrice = product.findLowestPromotionPrice(calculateLowestPromotionPriceService,
            promotions);

        return GetPromotionInfoResponse.mapProductToGetPromotionInfoResponseDto(product,
            promotions, lowestPrice);
    }
}
