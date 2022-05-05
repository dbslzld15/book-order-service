package org.prgrms.kdt.domain.order.exception;

public enum OrderItemExceptionType {
    ORDER_ITEM_NOT_SAVED("주문 상품 저장에 실패했습니다."),
    ORDER_ITEM_NOT_EXIST("존재하지 않는 주문상품입니다.");

    private final String msg;

    OrderItemExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
