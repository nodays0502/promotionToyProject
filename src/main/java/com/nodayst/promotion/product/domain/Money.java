package com.nodayst.promotion.product.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money implements Comparable<Money> {

    long value;

    public Money(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return value == money.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Money minus(Money money) {
        return new Money(this.value - money.getValue());
    }

    public Money percentageCalculate(long percent){
        return new Money(this.value * percent / 100);
    }

    @Override
    public int compareTo(Money m) {
        return Long.compare(this.getValue(),m.getValue());
    }

    @Embedded
    public static final Money ZERO = new Money(0L);

}