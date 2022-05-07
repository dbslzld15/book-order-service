package org.prgrms.kdt.domain.order.repository;

import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.order.entity.OrderItem;
import org.prgrms.kdt.domain.order.exception.OrderItemException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.prgrms.kdt.domain.order.exception.OrderItemExceptionType.*;

@Repository
public class JdbcOrderItemRepository implements OrderItemRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderItemRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(OrderItem orderItem) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = toParameterSource(orderItem);
        int savedRows = jdbcTemplate.update(
                "INSERT INTO order_item(total_price, order_quantity, order_id, item_id, created_at, modified_at) " +
                        "VALUES (:totalPrice, :orderQuantity, :orderId, :itemId, :createdAt, :modifiedAt)",
                source, keyHolder, new String[]{"order_item_id"}
        );
        if(savedRows != 1) {
            throw new OrderItemException(ORDER_ITEM_NOT_SAVED);
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        return jdbcTemplate.query("SELECT * FROM order_item WHERE order_id = :orderId AND is_deleted = 'N'",
                Collections.singletonMap("orderId", orderId), orderItemRowMapper);
    }

    @Override
    public void deleteById(long orderItemId) {
        jdbcTemplate.update("UPDATE order_item SET is_deleted = 'Y' WHERE order_item_id = :orderItemId",
                Collections.singletonMap("orderItemId", orderItemId));
    }

    private static final RowMapper<OrderItem> orderItemRowMapper = (rs, i) -> {
        long orderItemId = rs.getLong("order_item_id");
        long totalPrice = rs.getLong("total_price");
        int orderQuantity = rs.getInt("order_quantity");
        long orderId = rs.getLong("order_id");
        long itemId = rs.getLong("item_id");

        return OrderItem.builder()
                .orderItemId(orderItemId)
                .totalPrice(new Price(totalPrice))
                .orderQuantity(orderQuantity)
                .orderId(orderId)
                .itemId(itemId)
                .build();
    };

    private MapSqlParameterSource toParameterSource(OrderItem orderItem) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("totalPrice", orderItem.getTotalPrice().getPrice())
                .addValue("orderQuantity", orderItem.getOrderQuantity())
                .addValue("orderId", orderItem.getOrderId())
                .addValue("itemId", orderItem.getItemId())
                .addValue("createdAt", orderItem.getCreatedDateTime())
                .addValue("modifiedAt", orderItem.getModifiedDateTime());
        return parameters;
    }

    private Map<String, Object> toParamMap(OrderItem orderItem) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderItemId", orderItem.getOrderItemId());
        paramMap.put("totalPrice", orderItem.getTotalPrice().getPrice());
        paramMap.put("orderQuantity", orderItem.getOrderQuantity());
        paramMap.put("orderId", orderItem.getOrderId());
        paramMap.put("itemId", orderItem.getItemId());
        paramMap.put("createdAt", orderItem.getCreatedDateTime());
        paramMap.put("modifiedAt", orderItem.getModifiedDateTime());
        return paramMap;
    }
}
