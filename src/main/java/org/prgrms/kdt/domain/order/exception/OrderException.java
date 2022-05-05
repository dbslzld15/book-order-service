package org.prgrms.kdt.domain.order.exception;

public class OrderException extends RuntimeException{
    public OrderException(OrderExceptionType exceptionType) {
        super(exceptionType.getMsg());
    }
}
