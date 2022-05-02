package org.prgrms.kdt.domain.user.vo;

import lombok.Getter;

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
}
