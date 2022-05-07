package org.prgrms.kdt.domain.order.entity;

public enum OrderStatus {
    ACCEPTED("주문 접수"),
    SHIPPED("배송 중"),
    CANCELED("주문 취소");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
