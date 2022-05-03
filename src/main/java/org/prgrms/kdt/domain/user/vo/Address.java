package org.prgrms.kdt.domain.user.vo;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Address {
    private final String address;
    private static final int MAX_LENGTH = 100;

    public Address(String address) {
        validateAddress(address);
        this.address = address;
    }

    private void validateAddress(String address) {
        if(address.isBlank()){
            throw new IllegalArgumentException("주소에는 빈 공백만 들어올 수 없습니다.");
        }
        if(address.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("주소는 100자를 초과할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(getAddress(), address1.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }
}
