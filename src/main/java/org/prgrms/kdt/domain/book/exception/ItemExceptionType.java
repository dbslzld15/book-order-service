package org.prgrms.kdt.domain.book.exception;

public enum ItemExceptionType {
    ITEM_NOT_EXIST("존재하지 않는 아이템입니다."),
    ITEM_NOT_SAVED("아이템 저장에 실패했습니다.");

    private final String msg;

    ItemExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
