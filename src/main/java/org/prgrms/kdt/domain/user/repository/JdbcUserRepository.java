package org.prgrms.kdt.domain.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.entity.UserRole;
import org.prgrms.kdt.domain.user.exception.UserException;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.domain.user.vo.Email;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.domain.user.vo.Password;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.prgrms.kdt.domain.user.exception.UserExceptionType.USER_NOT_SAVED;
import static org.prgrms.kdt.global.utils.Utils.toLocalDateTime;

@Slf4j
@Repository
public class JdbcUserRepository implements UserRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = toParameterSource(user);
        int savedRows = jdbcTemplate.update(
                "INSERT INTO user(name, password, email, address, created_at, modified_at) " +
                        "VALUES (:name, :password, :email, :address, :createdAt, :modifiedAt)",
                source, keyHolder, new String[]{"user_id"}
        );
        if(savedRows != 1) {
            throw new UserException(USER_NOT_SAVED);
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update("UPDATE user " +
                "SET name = :name, password = :password, email = :email, address = :address, modified_at = :modifiedAt " +
                "WHERE user_id = :userId", toParamMap(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user " +
                    "WHERE email = :email AND is_deleted = 'N'", Collections.singletonMap("email", email), userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 이메일에 해당하는 고객 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(long userId) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user " +
                    "WHERE user_id = :userId AND is_deleted = 'N'", Collections.singletonMap("userId", userId), userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 아이디에 해당하는 고객 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("email", email);
            paramMap.put("password", password);

            User user = jdbcTemplate.queryForObject("SELECT * FROM user " +
                    "WHERE email = :email AND password = :password AND is_deleted = 'N'", paramMap, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 이메일에 해당하는 고객 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM user", Collections.emptyMap());
    }

    private static final RowMapper<User> userRowMapper = (rs, i) -> {
        long userId = rs.getLong("user_id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String role = rs.getString("role");

        return User.builder()
                .userId(userId)
                .name(new Name(name))
                .password(new Password(password))
                .email(new Email(email))
                .address(new Address(address))
                .userRole(UserRole.valueOf(role))
                .build();
    };

    private MapSqlParameterSource toParameterSource(User user) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", user.getName().getName())
                .addValue("password", user.getPassword().getPassword())
                .addValue("email", user.getEmail().getEmail())
                .addValue("address", user.getAddress().getAddress())
                .addValue("createdAt", user.getCreatedDateTime())
                .addValue("modifiedAt", user.getModifiedDateTime());
        return parameters;
    }

    private Map<String, Object> toParamMap(User user) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getUserId());
        paramMap.put("name", user.getName().getName());
        paramMap.put("password", user.getPassword().getPassword());
        paramMap.put("email", user.getEmail().getEmail());
        paramMap.put("address", user.getAddress().getAddress());
        paramMap.put("createdAt", user.getCreatedDateTime());
        paramMap.put("modifiedAt", user.getModifiedDateTime());
        return paramMap;
    }
}
