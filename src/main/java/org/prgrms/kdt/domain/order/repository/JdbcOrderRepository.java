package org.prgrms.kdt.domain.order.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.order.entity.Order;
import org.prgrms.kdt.domain.order.entity.OrderStatus;
import org.prgrms.kdt.domain.order.exception.OrderException;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.global.utils.Utils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.kdt.domain.order.exception.OrderExceptionType.ORDER_NOT_SAVED;

@Slf4j
@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(Order order) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = toParameterSource(order);
        int savedRows = jdbcTemplate.update(
                "INSERT INTO orders(address, order_status, order_date, user_id, created_at, modified_at) " +
                        "VALUES (:address, :orderStatus, :orderDate, :userId, :createdAt, :modifiedAt)",
                source, keyHolder, new String[]{"order_id"}
        );
        if(savedRows != 1) {
            throw new OrderException(ORDER_NOT_SAVED);
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Order order) {
        return jdbcTemplate.update("UPDATE orders " +
                "SET address = :address, order_status = :orderStatus, order_date = :orderDate, user_id = :userId " +
                "WHERE order_id = :orderId", toParamMap(order));
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM orders " +
                "WHERE is_deleted = 'N'", Collections.emptyMap(), orderRowMapper);
    }

    @Override
    public Optional<Order> findById(long orderId) {
        try {
            Order order = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = :orderId AND is_deleted = 'N'",
                    Collections.singletonMap("orderId", orderId), orderRowMapper);
            return Optional.of(order);
        } catch (EmptyResultDataAccessException e) {
            log.error("입력받은 아이디에 해당하는 주문 정보가 존재하지 않습니다", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAllByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE user_id = :userId AND is_deleted = 'N'",
                Collections.singletonMap("userId", userId), orderRowMapper);
    }

    @Override
    public void deleteById(long orderId) {
        jdbcTemplate.update("UPDATE orders SET is_deleted = 'Y' WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId));
    }

    private static final RowMapper<Order> orderRowMapper = (rs, i) -> {
        long orderId = rs.getLong("order_id");
        String address = rs.getString("address");
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        LocalDateTime orderDate = Utils.toLocalDateTime(rs.getTimestamp("order_date"));
        long userId = rs.getLong("user_id");

        return Order.builder()
                .orderId(orderId)
                .address(new Address(address))
                .orderStatus(orderStatus)
                .orderDateTime(orderDate)
                .userId(userId)
                .build();
    };

    private MapSqlParameterSource toParameterSource(Order order) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("address", order.getAddress().getAddress())
                .addValue("orderStatus", order.getOrderStatus().toString())
                .addValue("orderDate", order.getOrderDateTime())
                .addValue("userId", order.getUserId())
                .addValue("createdAt", order.getCreatedDateTime())
                .addValue("modifiedAt", order.getModifiedDateTime());
        return parameters;
    }

    private Map<String, Object> toParamMap(Order order) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.getOrderId());
        paramMap.put("address", order.getAddress().getAddress());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("orderDate", order.getOrderDateTime());
        paramMap.put("userId", order.getUserId());
        paramMap.put("createdAt", order.getCreatedDateTime());
        paramMap.put("modifiedAt", order.getModifiedDateTime());

        return paramMap;
    }
}
