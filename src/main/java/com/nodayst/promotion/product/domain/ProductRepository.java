package com.nodayst.promotion.product.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends Repository<Product, Long> {

    void save(Product product);

    void deleteById(long productId);

    boolean existsById(long productId);

    Optional<Product> findById(long productId);

    @Query("select p from Product p "
        + "where p.type in (:types)")
    List<Product> findProductsByType(@Param("types") List<ProductType> types);

}

