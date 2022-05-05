package org.prgrms.kdt.domain.order.exception;

public class OrderItemException extends RuntimeException{
    public OrderItemException(OrderItemExceptionType exceptionType) {
        super(exceptionType.getMsg());
    }
}
