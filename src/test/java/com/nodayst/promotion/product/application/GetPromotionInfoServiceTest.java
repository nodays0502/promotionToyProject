package com.nodayst.promotion.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.product.ui.dto.GetPromotionInfoResponse;
import com.nodayst.promotion.promotion.domain.Period;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionType;
import java.time.LocalDateTime;
import java.time.Month;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetPromotionInfoServiceTest {

    @Autowired
    private GetPromotionInfoService getPromotionInfoService;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    @Transactional
    void init() {
        Product product = Product.withOutId("마우스", "버티컬 마우스", new Money(17_000L),
            ProductType.NORMAL);
        em.persist(product);
        long productId = em.createQuery("select p.id from Product p where p.name = '마우스'",
            Long.class).getSingleResult();
        Promotion promotion1 = Promotion.withOutId(
            new Period(LocalDateTime.now(), LocalDateTime.of(2030, Month.MAY, 02, 00, 00, 00)),
            PromotionType.PRICE, 10_000, productId);
        em.persist(promotion1);
        Promotion promotion2 = Promotion.withOutId(
            new Period(LocalDateTime.now(), LocalDateTime.of(2030, Month.MAY, 02, 00, 00, 00)),
            PromotionType.RATE, 10, productId);
        em.persist(promotion2);
    }

    @Test
    @Transactional
    public void getPromotionInfoTest(){
        long productId = em.createQuery("select p.id from Product p where p.name = '마우스'",Long.class)
            .getSingleResult();
        GetPromotionInfoResponse getPromotionInfoResponse = getPromotionInfoService.findPromotionLowestPrice(
            productId);
        assertEquals(7_000L,getPromotionInfoResponse.getPromotionAppliedMoney());
    }
}