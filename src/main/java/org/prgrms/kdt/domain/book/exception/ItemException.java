package org.prgrms.kdt.domain.book.exception;

public class ItemException extends RuntimeException{
    private final int statusCode;

    public ItemException(ItemExceptionType exceptionType) {
        super(exceptionType.getMsg());
        this.statusCode = exceptionType.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
