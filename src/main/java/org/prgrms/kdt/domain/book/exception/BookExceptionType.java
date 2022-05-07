package org.prgrms.kdt.domain.book.exception;

public enum BookExceptionType {
    BOOK_NOT_EXIST("존재하지 않는 도서입니다.", 400),
    BOOK_NOT_SAVED("도서 저장에 실패했습니다.", 500);

    private final String msg;
    private final int statusCode;

    BookExceptionType(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
