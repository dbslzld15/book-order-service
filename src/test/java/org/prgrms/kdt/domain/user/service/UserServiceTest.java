package org.prgrms.kdt.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.repository.UserRepository;
import org.prgrms.kdt.domain.user.request.UserCreateRequest;
import org.prgrms.kdt.domain.user.request.UserLoginRequest;
import org.prgrms.kdt.domain.user.request.UserPwResetRequest;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.domain.user.vo.Email;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.domain.user.vo.Password;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void save() throws Exception {
        //given
        Long customerId = 1L;
        UserCreateRequest request = new UserCreateRequest(
                "park", "park@naver.com", "경기도 고양시 일산동구");
        //when
        when(userRepository.save(any())).thenReturn(customerId);
        long savedId = userService.save(request);
        //then
        assertThat(savedId).isEqualTo(customerId);
    }

    @Test
    void updatePassword() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        User user = User.builder()
                .userId(1L)
                .name(new Name("kim"))
                .address(new Address("서울 특별시"))
                .password(new Password("pbm49431380@#"))
                .email(new Email("kim123@naver.com"))
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        UserPwResetRequest request = new UserPwResetRequest("park@naver.com", "park12345678@");
        //when
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        userService.updatePassword(request);
        //then
        verify(userRepository, times(1)).update(any());
    }

    @Test
    void getUserByLogin() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String password = "pbm49431380@#";
        String email = "kim123@naver.com";
        User user = User.builder()
                .userId(1L)
                .name(new Name("kim"))
                .address(new Address("서울 특별시"))
                .password(new Password(password))
                .email(new Email(email))
                .createdDateTime(now)
                .modifiedDateTime(now)
                .build();
        UserLoginRequest request = new UserLoginRequest(email, password);
        //when
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));
        //then
        User findUser = userService.getUserByLogin(request);
        assertThat(findUser).usingRecursiveComparison().isEqualTo(user);
    }


}