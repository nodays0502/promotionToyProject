package com.nodayst.promotion.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
import com.nodayst.promotion.product.ui.dto.ProductDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetProductServiceTest {

    @Autowired
    private GetProductService getProductService;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    @Transactional
    void init(){
        Product normalProduct = Product.withOutId("마우스", "버티컬 마우스", new Money(17_000L),
            ProductType.NORMAL);
        em.persist(normalProduct);
        Product businessProduct = Product.withOutId("키보드", "갈축 키보드", new Money(19_000L),
            ProductType.BUSINESS);
        em.persist(businessProduct);
        User normalUser = User.withOutId("노말", UserType.NORMAL, UserState.NORMAL);
        em.persist(normalUser);
        User businessUser = User.withOutId("비즈니스", UserType.BUSINESS, UserState.NORMAL);
        em.persist(businessUser);
    }

    @Test
    @Transactional
    void getProductNormalTest(){
        User normalUser = em.createQuery("select u from User u where u.name = '노말'", User.class)
            .getSingleResult();
        List<ProductDto> products = getProductService.getProduct(normalUser.getId());
        assertEquals(1,products.size());
        assertEquals("마우스",products.get(0).getName());
    }
    @Test
    @Transactional
    void getProductBusinessTest(){
        User businessUser = em.createQuery("select u from User u where u.name = '비즈니스'", User.class)
            .getSingleResult();
        List<ProductDto> products = getProductService.getProduct(businessUser.getId());
        assertEquals(2,products.size());
    }
}