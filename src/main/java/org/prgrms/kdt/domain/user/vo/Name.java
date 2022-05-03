package org.prgrms.kdt.domain.user.vo;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Name {
    private final String name;
    private static final int MIN_LENGTH = 2;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if(name.isBlank()){
            throw new IllegalArgumentException("이름에는 빈 공백만 들어올 수 없습니다.");
        }
        if(name.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("이름은 2자 이상이어야합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(getName(), name1.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
