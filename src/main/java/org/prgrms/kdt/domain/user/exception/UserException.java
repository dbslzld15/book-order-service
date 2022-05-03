package org.prgrms.kdt.domain.user.exception;

public class UserException extends RuntimeException {
    public UserException(UserExceptionType userExceptionType) {
        super(userExceptionType.getMsg());
    }
}
