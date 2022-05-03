package org.prgrms.kdt.domain.book.vo;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Price {
    private final long price;
    private static final long MIN_VALUE = 0;

    public Price(long price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(long price) {
        if(price <= MIN_VALUE) {
            throw new IllegalArgumentException("금액은 0원 이하가 될 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return getPrice() == price1.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }
}
