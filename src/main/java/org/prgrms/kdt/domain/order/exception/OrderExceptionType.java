package org.prgrms.kdt.domain.order.exception;

public enum OrderExceptionType {
    ORDER_NOT_EXIST("존재하지 않는 주문입니다."),
    ORDER_NOT_SAVED("주문 저장에 실패했습니다."),
    ORDER_CAN_NOT_CANCELED("해당 주문은 취소할 수 없습니다."),
    ORDER_NOT_ENOUGH_STOCK("남은 재고 수량이 없습니다");

    private final String msg;

    OrderExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
