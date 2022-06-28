package com.nodayst.promotion.product.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
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
class productControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @BeforeEach
    @Transactional
    public void init() {
        User normalUser = User.withOutId("일반유저", UserType.NORMAL, UserState.NORMAL);
        User businessUser = User.withOutId("비즈니스유저", UserType.BUSINESS, UserState.NORMAL);
        em.persist(normalUser);
        em.persist(businessUser);

        String name = "키보드";
        String desc = "청축 키보드";
        long price = 17000L;
        ProductType type = ProductType.NORMAL;
        Product product = Product.withOutId(name, desc, new Money(price), type);
        em.persist(product);
    }

    @Test
    @Transactional
    public void registerProductTest() throws Exception {
        String name = "마우스";
        String desc = "버티컬 마우스";
        long price = 17000L;
        ProductType type = ProductType.NORMAL;
        mvc.perform(post("/api/product")
                .content(
                    "{ \"name\":\"" + name + "\","
                        + "\"desc\":\"" + desc + "\","
                        + "\"price\":\"" + price + "\","
                        + "\"type\":\"" + type.name() + "\"}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void getProductTest() throws Exception {
        long userId = em.createQuery("select u.id from User u where u.name = :name",
                Long.class)
            .setParameter("name", "일반유저").getSingleResult();
        mvc.perform(get("/api/product/" + userId))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getPromotionInfoTest() throws Exception {
        long productId = em.createQuery("select p.id from Product p where p.name = :name",
                Long.class)
            .setParameter("name", "키보드").getSingleResult();
        mvc.perform(get("/api/product/" + productId + "/promotionInfo"))
            .andExpect(status().isOk());
    }
}