package com.nodayst.promotion.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.product.ui.dto.RegisterProductRequest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RegisterProductServiceTest {

    @Autowired
    private RegisterProductService registerProductService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void registerProductTest() {
        RegisterProductRequest registerProductRequest = new RegisterProductRequest("마우스", "버티컬 마우스",
            17_000,
            ProductType.NORMAL);
        registerProductService.registerProduct(registerProductRequest);
        List<Product> products = em.createQuery("select p from Product p where p.name = '마우스'",
            Product.class).getResultList();
        assertEquals(1,products.size());
    }
}
