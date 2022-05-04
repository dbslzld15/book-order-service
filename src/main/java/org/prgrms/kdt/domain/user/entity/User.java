package org.prgrms.kdt.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.domain.user.vo.Email;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.domain.user.vo.Password;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class User extends BaseEntity {
    private Long userId;
    private Name name;
    private Password password;
    private Email email;
    private Address address;
    private UserRole userRole;

    @Builder
    public User(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                Long userId, Name name, Password password, Email email,
                Address address, UserRole userRole) {
        super(createdDateTime, modifiedDateTime);
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.userRole = userRole == null ? UserRole.USER: userRole;
    }
}
