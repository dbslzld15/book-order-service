package org.prgrms.kdt.domain.book.exception;

public enum ItemExceptionType {
    ITEM_NOT_EXIST("존재하지 않는 아이템입니다.", 400),
    ITEM_NOT_SAVED("아이템 저장에 실패했습니다.", 500);

    private final String msg;
    private final int statusCode;

    ItemExceptionType(String msg, int statusCode) {
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
