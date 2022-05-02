package org.prgrms.kdt.domain.book.vo;

import lombok.Getter;

@Getter
public class Title {
    private final String title;
    private static final int MAX_LENGTH = 50;

    public Title(String title) {
        validateTitle(title);
        this.title = title;
    }

    private void validateTitle(String title) {
        if(title.isBlank()) {
            throw new IllegalArgumentException("책 제목에는 빈 공백만 들어올 수 없습니다.");
        }
        if(title.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("책 제목은 50자를 초과할 수 없습니다.");
        }
    }
}
