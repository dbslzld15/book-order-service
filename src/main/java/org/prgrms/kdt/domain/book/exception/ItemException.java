package org.prgrms.kdt.domain.book.exception;

public class ItemException extends RuntimeException{
    public ItemException(ItemExceptionType exceptionType) {
        super(exceptionType.getMsg());
    }
}
