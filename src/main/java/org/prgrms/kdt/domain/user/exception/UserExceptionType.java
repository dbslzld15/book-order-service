package org.prgrms.kdt.domain.user.exception;

public enum UserExceptionType {
    USER_NOT_EXIST("존재하지 않는 고객입니다.", 400),
    USER_PASSWORD_INCORRECT("패스워드가 일치하지 않습니다.", 400),
    USER_NOT_SAVED("고객 저장에 실패했습니다.", 500);

    private final String msg;
    private final int statusCode;

    UserExceptionType(String msg, int statusCode) {
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
