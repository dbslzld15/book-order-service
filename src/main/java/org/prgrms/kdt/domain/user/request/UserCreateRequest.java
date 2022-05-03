package org.prgrms.kdt.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserCreateRequest {
    private String name;
    private String email;
    private String address;
}
