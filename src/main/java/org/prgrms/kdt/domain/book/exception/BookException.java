package org.prgrms.kdt.domain.book.exception;

public class BookException extends RuntimeException{
    public BookException(BookExceptionType exceptionType) {
        super(exceptionType.getMsg());
    }
}
