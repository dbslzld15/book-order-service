package org.prgrms.kdt.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter @Setter
public class UserPwResetRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
