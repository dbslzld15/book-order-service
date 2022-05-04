package org.prgrms.kdt.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.exception.UserException;
import org.prgrms.kdt.domain.user.repository.UserRepository;
import org.prgrms.kdt.domain.user.request.UserCreateRequest;
import org.prgrms.kdt.domain.user.request.UserLoginRequest;
import org.prgrms.kdt.domain.user.request.UserPwResetRequest;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.domain.user.vo.Email;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.domain.user.vo.Password;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;
import static org.prgrms.kdt.domain.user.exception.UserExceptionType.USER_NOT_EXIST;
import static org.prgrms.kdt.domain.user.exception.UserExceptionType.USER_PASSWORD_INCORRECT;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public long save(UserCreateRequest request) {
        User user = User.builder()
                .name(new Name(request.getName()))
                .address(new Address(request.getAddress()))
                .email(new Email(request.getEmail()))
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(UserPwResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
        User updateUser = User.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .address(user.getAddress())
                .password(new Password(request.getPassword()))
                .build();
        userRepository.update(updateUser);
        log.info("update Password, user email: {}", request.getEmail());
    }

    @Transactional
    public User getUserByLogin(UserLoginRequest request) {
        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
        return userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new UserException(USER_PASSWORD_INCORRECT));
    }
}
