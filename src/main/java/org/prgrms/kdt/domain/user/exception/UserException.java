package org.prgrms.kdt.domain.user.exception;

public class UserException extends RuntimeException {
    private final int statusCode;

    public UserException(UserExceptionType userExceptionType) {
        super(userExceptionType.getMsg());
        this.statusCode = userExceptionType.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
