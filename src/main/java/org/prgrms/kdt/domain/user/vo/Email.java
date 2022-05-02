package org.prgrms.kdt.domain.user.vo;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {
    private final String email;
    private static final Pattern emailPattern = Pattern.compile("/\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b/gi");
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
}
