package com.nodayst.promotion.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DeleteProductServiceTest {

    @Autowired
    private DeleteProductService deleteProductService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void deleteProductTest() {
        Product product = Product.withOutId("마우스", "버티컬 마우스", new Money(17_000L),
            ProductType.NORMAL);
        em.persist(product);
        long productId = em.createQuery("select p.id from Product p where p.name = '마우스'",
            Long.class).getSingleResult();
        deleteProductService.deleteProduct(productId);
        List<Product> products = em.createQuery("select p from Product p where p.name = '마우스'",
            Product.class).getResultList();
        assertEquals(0, products.size());
    }
}