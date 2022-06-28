package com.nodayst.promotion.promotion.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionType;
import com.nodayst.promotion.promotion.ui.dto.RegisterPromotionRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RegisterPromotionServiceTest {

    @Autowired
    private RegisterPromotionService registerPromotionService;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    @Transactional
    void init() {
        Product product = Product.withOutId("마우스", "버티컬 마우스", new Money(17_000L),
            ProductType.NORMAL);
        em.persist(product);
    }

    @Test
    @Transactional
    public void registerPromotionTest() {
        long productId = em.createQuery("select p.id from Product p where p.name = :name",
                Long.class).setParameter("name", "마우스")
            .getSingleResult();
        RegisterPromotionRequest registerPromotionRequest = new RegisterPromotionRequest(productId,
            LocalDateTime.of(2022, Month.MAY, 21, 12, 0, 0),
            LocalDateTime.of(2022, Month.OCTOBER, 05, 12, 0, 0),
            PromotionType.PRICE, 10);
        registerPromotionService.registerPromotion(registerPromotionRequest);
        List<Promotion> promotions = em.createQuery(
                "select pm from Promotion pm where pm.productId = :productId",
                Promotion.class)
            .setParameter("productId", productId).getResultList();
        assertEquals(1, promotions.size());
    }
}