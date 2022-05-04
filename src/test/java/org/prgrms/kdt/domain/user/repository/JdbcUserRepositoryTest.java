package org.prgrms.kdt.domain.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.domain.user.vo.Email;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.domain.user.vo.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcUserRepositoryTest {

    @Autowired
    JdbcUserRepository userRepository;

    @AfterEach
    void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    void save() throws Exception {
        //given
        User user = User.builder()
                .name(new Name("park"))
                .address(new Address("경기도 고양시"))
                .password(new Password("pbm49431380@"))
                .email(new Email("park1534@naver.com"))
                .build();
        //when
        long savedId = userRepository.insert(user);
        Optional<User> findUser = userRepository.findById(savedId);
        //then
        assertThat(findUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void update() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        User user = User.builder()
                .name(new Name("park"))
                .address(new Address("경기도 고양시"))
                .password(new Password("pbm49431380@"))
                .email(new Email("park1534@naver.com"))
                .build();
        long savedId = userRepository.insert(user);
        User updateUser = User.builder()
                .userId(savedId)
                .name(new Name("kim"))
                .address(new Address("서울 특별시"))
                .password(new Password("pbm49431380@#"))
                .email(new Email("kim123@naver.com"))
                .build();
        //when
        int updatedRows = userRepository.update(updateUser);
        Optional<User> findUser = userRepository.findById(savedId);
        //then
        assertThat(updatedRows).isEqualTo(1);
        assertThat(findUser.get().getEmail()).isEqualTo(updateUser.getEmail());
    }

    @Test
    void findByEmail() throws Exception {
        //given
        String email = "park1534@naver.com";
        User user = User.builder()
                .name(new Name("park"))
                .address(new Address("경기도 고양시"))
                .password(new Password("pbm49431380@"))
                .email(new Email(email))
                .build();
        userRepository.insert(user);
        //when
        Optional<User> findUser = userRepository.findByEmail(email);
        //then
        assertThat(findUser.get().getEmail()).usingRecursiveComparison().isEqualTo(user.getEmail());
    }

    @Test
    void findByEmailAndPassword() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String email = "park1534@naver.com";
        String password = "pbm49431380@";
        User user = User.builder()
                .name(new Name("park"))
                .address(new Address("경기도 고양시"))
                .password(new Password(password))
                .email(new Email(email))
                .build();
        userRepository.insert(user);
        //when
        Optional<User> findUser = userRepository.findByEmailAndPassword(email, password);
        //then
        assertThat(findUser.get().getEmail()).isEqualTo(user.getEmail());
    }
}