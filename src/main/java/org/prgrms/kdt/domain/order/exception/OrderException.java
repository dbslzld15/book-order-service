package org.prgrms.kdt.domain.order.exception;

public class OrderException extends RuntimeException {
    private final int statusCode;

    public OrderException(OrderExceptionType exceptionType) {
        super(exceptionType.getMsg());
        this.statusCode = exceptionType.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
