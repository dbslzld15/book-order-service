package org.prgrms.kdt.domain.user.repository;

import org.prgrms.kdt.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {

    long save(User user);

    int update(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);

    Optional<User> findByEmailAndPassword(String email, String password);

    void deleteAll();
}
