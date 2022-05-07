package org.prgrms.kdt.domain.book.exception;

public class BookException extends RuntimeException {
    private final int statusCode;

    public BookException(BookExceptionType exceptionType) {
        super(exceptionType.getMsg());
        this.statusCode = exceptionType.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
