package org.prgrms.kdt.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserLoginRequest {
    private String email;
    private String password;
}
