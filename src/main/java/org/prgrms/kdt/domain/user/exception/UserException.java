package org.prgrms.kdt.domain.user.exception;

public class UserException extends RuntimeException {
    private int statusCode;

    public UserException(UserExceptionType userExceptionType) {
        super(userExceptionType.getMsg());
        this.statusCode = userExceptionType.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
