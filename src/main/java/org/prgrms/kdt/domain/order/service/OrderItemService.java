package org.prgrms.kdt.domain.order.service;

import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.order.entity.OrderItem;
import org.prgrms.kdt.domain.order.repository.OrderItemRepository;
import org.prgrms.kdt.domain.order.request.OrderItemCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void save(long orderId, OrderItemCreateRequest createRequest) {
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderId)
                .itemId(createRequest.getItemId())
                .orderQuantity(createRequest.getOrderQuantity())
                .totalPrice(new Price(createRequest.getTotalPrice()))
                .build();
        orderItemRepository.insert(orderItem);
    }

    public List<OrderItem> getAllByOrderId(long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public void remove(long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
