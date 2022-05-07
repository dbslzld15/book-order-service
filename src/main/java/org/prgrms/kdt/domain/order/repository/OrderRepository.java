package org.prgrms.kdt.domain.order.repository;

import org.prgrms.kdt.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    long insert(Order order);

    Optional<Order> findById(long orderId);

    List<Order> findAllByUserId(long userId);

    void deleteById(long orderId);

    int update(Order order);

    List<Order> findAll();
}
