package com.nodayst.promotion.promotion.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.nodayst.promotion.product.domain.CalculateLowestPromotionPriceService;
import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromotionTest {
    @Test
    void findLowestPriceTest() {
        Money money = new Money(17_000L);
        Product product = Product.withOutId("마우스", "버티컬 마우스", money, ProductType.NORMAL);
        Period period = new Period(LocalDateTime.now(), LocalDateTime.now());
        Promotion promotion1 = Promotion.withOutId(period, PromotionType.RATE, 10,1L);
        Promotion promotion2 = Promotion.withOutId(period, PromotionType.PRICE, 10_000,1L);
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        CalculateLowestPromotionPriceService calculateLowestPromotionPriceService = new CalculateLowestPromotionPriceService();

        assertEquals(new Money(7_000L), product.findLowestPromotionPrice(calculateLowestPromotionPriceService,promotions));
    }
}