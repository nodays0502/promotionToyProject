package com.nodayst.promotion.promotion.domain;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
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
@Getter
@Table(name = "PROMOTION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROMOTION_ID")
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "START_DATE_TIME")),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "END_DATE_TIME"))
    })
    private Period period;

    @Column(name = "PROMOTION_TYPE")
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Column(name = "VALUE")
    private long value;

    //    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "PRODUCT_ID")
    private long productId;

    public Promotion(Long id, Period period, PromotionType type, long value, long productId) {
        this.id = id;
        this.period = period;
        this.type = type;
        this.value = value;
        this.productId = productId;
    }

    public static Promotion withOutId(Period period, PromotionType type, long value,
        long productId) {
        return new Promotion(null, period, type, value, productId);
    }

    public static Promotion withId(Long id, Period period, PromotionType type, long value,
        long productId) {
        return new Promotion(id, period, type, value, productId);
    }

    public Money calculatePrice(Money money) {
        if (type == PromotionType.RATE) { // 정률제
            long percent = 100 - value;
            return money.percentageCalculate(percent);
        } else {                                // 정액제
            return money.minus(new Money(value));
        }
    }

    public void registerProduct(Product product) {
        this.productId = product.getId();
    }
}