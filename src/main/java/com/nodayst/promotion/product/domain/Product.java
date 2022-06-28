package com.nodayst.promotion.product.domain;

import com.nodayst.promotion.promotion.domain.Promotion;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "DESC")
    private String desc;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "PRICE"))
    })
    private Money price;

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    public Product(Long id, String name, String desc, Money price,
        ProductType type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.type = type;
    }

    public Money findLowestPromotionPrice(
        CalculateLowestPromotionPriceService calculateLowestPromotionPriceService,
        List<Promotion> promotions) {
        return calculateLowestPromotionPriceService.calculateLowestPromotionPrice(this, promotions);
    }

    public static Product withOutId(String name, String desc, Money price, ProductType type) {
        return new Product(null, name, desc, price, type);
    }

    public static Product withId(Long id, String name, String desc, Money price, ProductType type) {
        return new Product(id, name, desc, price, type);
    }

}
