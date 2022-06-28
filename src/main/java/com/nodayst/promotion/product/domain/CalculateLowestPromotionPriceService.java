package com.nodayst.promotion.product.domain;

import com.nodayst.promotion.promotion.domain.Promotion;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CalculateLowestPromotionPriceService {
    public Money calculateLowestPromotionPrice(Product product, List<Promotion> promotions){
        List<Money> collect = promotions.stream().map((p) -> {
            return p.calculatePrice(product.getPrice());
        }).filter(m -> m.compareTo(Money.ZERO) > 0).sorted().collect(Collectors.toList());
        if (collect.size() != 0) {
            return collect.get(0);
        }
        return product.getPrice();
    }
}
