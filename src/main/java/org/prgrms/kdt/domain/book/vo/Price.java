package org.prgrms.kdt.domain.book.vo;

import lombok.Getter;

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
}
