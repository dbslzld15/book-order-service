package org.prgrms.kdt.domain.book.vo;

import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title1 = (Title) o;
        return Objects.equals(getTitle(), title1.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
