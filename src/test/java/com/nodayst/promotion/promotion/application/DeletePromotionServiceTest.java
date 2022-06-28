package com.nodayst.promotion.promotion.application;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.promotion.domain.Period;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionType;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DeletePromotionServiceTest {

    @Autowired
    private DeletePromotionService deletePromotionService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void deletePromotionTest() {
        Product product = Product.withOutId("마우스", "버티컬 마우스", new Money(17_000L),
            ProductType.NORMAL);
        em.persist(product);
        long productId = em.createQuery("select p.id from Product p where p.name = '마우스'",
            Long.class).getSingleResult();
        Promotion promotion = Promotion.withOutId(
            new Period(LocalDateTime.now(), LocalDateTime.now()),
            PromotionType.PRICE, 10_000, productId);

        em.persist(promotion);
        long promotionId = em.createQuery(
                "select p.id from Promotion p where p.productId = :productId", Long.class)
            .setParameter("productId", productId).getSingleResult();
        deletePromotionService.deletePromotion(promotionId);
        assertNull(em.find(Promotion.class,promotionId));
    }
}
