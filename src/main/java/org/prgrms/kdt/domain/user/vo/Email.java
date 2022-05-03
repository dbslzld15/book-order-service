package org.prgrms.kdt.domain.user.vo;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Email {
    private final String email;
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final int MAX_LENGTH = 50;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email) {
        if(!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 주소를 확인해주세요");
        }
        if(email.length() > MAX_LENGTH){
            throw new IllegalArgumentException("이메일 주소는 50자를 초과할 수 없습니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(getEmail(), email1.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
