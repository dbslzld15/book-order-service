package org.prgrms.kdt.domain.order.repository;

import org.prgrms.kdt.domain.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    long insert(OrderItem orderItem);

    List<OrderItem> findByOrderId(long orderId);

    void deleteById(long orderItemId);
}
