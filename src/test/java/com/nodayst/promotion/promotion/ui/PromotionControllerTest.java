package com.nodayst.promotion.promotion.ui;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.promotion.domain.Period;
import com.nodayst.promotion.promotion.domain.Promotion;
import com.nodayst.promotion.promotion.domain.PromotionType;
import java.time.LocalDateTime;
import java.time.Month;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
class PromotionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @BeforeEach
    @Transactional
    public void init() {
        String name = "마우스";
        String desc = "버티컬 마우스";
        long price = 17000L;
        ProductType type = ProductType.NORMAL;
        Product product = Product.withOutId(name, desc, new Money(price), type);
        em.persist(product);
    }

    @Test
    @Transactional
    public void registerPromotionTest() throws Exception {
        String name = "마우스";
        long productId = em.createQuery("select p.id from Product p where p.name = :name",
                Long.class)
            .setParameter("name", name)
            .getSingleResult();
        mvc.perform(post("/api/promotion")
                .content(
                    "{ \"productId\":\"" + productId + "\","
                        + "\"startDateTime\":\"" + "2022-05-22T12:00:00" + "\","
                        + "\"endDateTime\":\"" + "2023-05-22T12:00:00" + "\","
                        + "\"type\":\"" + PromotionType.RATE.name() + "\","
                        + "\"value\":\"" + 10 + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void deletePromotionTest() throws Exception {
        Period period = new Period(LocalDateTime.of(2022, Month.MAY, 2, 12, 0),
            LocalDateTime.of(2023, Month.MAY, 2, 12, 0));
        PromotionType type = PromotionType.RATE;
        long value = 10L;
        Long productId = em.createQuery("select p.id from Product p where p.name = '마우스'",
            Long.class).getSingleResult();
        Promotion promotion = Promotion.withOutId(period, type, value,productId);
        em.persist(promotion);
        long promotionId = em.createQuery("select p.id from Promotion p", Long.class)
            .getSingleResult();
        mvc.perform(delete("/api/promotion/"+promotionId))
            .andExpect(status().isOk());
        assertNull(em.find(Promotion.class,promotionId));
    }
}