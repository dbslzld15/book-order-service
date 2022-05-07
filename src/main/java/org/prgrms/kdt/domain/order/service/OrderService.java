package org.prgrms.kdt.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.book.entity.Item;
import org.prgrms.kdt.domain.book.service.ItemService;
import org.prgrms.kdt.domain.order.entity.Order;
import org.prgrms.kdt.domain.order.entity.OrderItem;
import org.prgrms.kdt.domain.order.exception.OrderException;
import org.prgrms.kdt.domain.order.repository.OrderRepository;
import org.prgrms.kdt.domain.order.request.OrderCreateRequest;
import org.prgrms.kdt.domain.order.request.OrderItemCreateRequest;
import org.prgrms.kdt.domain.order.request.OrderUpdateRequest;
import org.prgrms.kdt.domain.user.vo.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.prgrms.kdt.domain.order.entity.OrderStatus.*;
import static org.prgrms.kdt.domain.order.exception.OrderExceptionType.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.itemService = itemService;
    }

    @Transactional
    public void save(long userId, OrderCreateRequest createRequest) {
        List<OrderItemCreateRequest> orderItems = createRequest.getOrderItems();
        checkRemainStocks(orderItems);
        Order order = Order.builder()
                .userId(userId)
                .address(new Address(createRequest.getAddress()))
                .orderStatus(ACCEPTED)
                .orderDateTime(now())
                .build();
        long orderId = orderRepository.insert(order);
        orderItems.forEach(orderItemRequest ->
            orderItemService.save(orderId, orderItemRequest)
        );
    }

    @Transactional
    public void cancel(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ORDER_NOT_EXIST));
        if(order.getOrderStatus() != ACCEPTED){
            throw new OrderException(ORDER_CAN_NOT_CANCELED);
        }
        Order canceledOrder = Order.builder()
                .orderStatus(CANCELED)
                .orderId(orderId)
                .userId(order.getUserId())
                .orderDateTime(order.getOrderDateTime())
                .address(order.getAddress())
                .build();
        orderRepository.update(canceledOrder);
        restock(orderId);
        log.info("cancel order, order id: {}", orderId);
    }

    public List<Order> getHistory(long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        orders.forEach(order -> {
            List<OrderItem> orderItems = orderItemService.getAllByOrderId(order.getOrderId());
            order.setOrderItems(orderItems);
        });
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            List<OrderItem> orderItems = orderItemService.getAllByOrderId(order.getOrderId());
            order.setOrderItems(orderItems);
        });
        return orders;
    }

    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ORDER_NOT_EXIST));
    }

    @Transactional
    public void remove(long orderId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ORDER_NOT_EXIST));
        orderRepository.deleteById(orderId);
        List<OrderItem> orderItems = orderItemService.getAllByOrderId(orderId);
        orderItems.forEach(orderItem -> orderItemService.remove(orderItem.getOrderItemId()));
        log.info("delete order, order id: {}", orderId);
    }

    @Transactional
    public void update(long orderId, OrderUpdateRequest updateRequest) {
        Order order = Order.builder()
                .orderId(orderId)
                .orderStatus(updateRequest.getOrderStatus())
                .orderDateTime(updateRequest.getOrderDateTime())
                .userId(updateRequest.getUserId())
                .address(new Address(updateRequest.getAddress()))
                .build();
        orderRepository.update(order);
        log.info("update order, order id: {}", orderId);
    }

    private void checkRemainStocks(List<OrderItemCreateRequest> orderItems) {
        orderItems.forEach(orderItem -> {
            Item item = itemService.getByItemId(orderItem.getItemId());
            int remainQuantity = item.getStockQuantity() - orderItem.getOrderQuantity();
            if(remainQuantity < 0){
                throw new OrderException(ORDER_NOT_ENOUGH_STOCK);
            }
            itemService.update(item.getItemId(), item.getPrice().getPrice(), remainQuantity);
        });
    }

    private void restock(long orderId) {
        List<OrderItem> orderItems = orderItemService.getAllByOrderId(orderId);
        orderItems.forEach(orderItem -> {
            int orderQuantity = orderItem.getOrderQuantity();
            Item item = itemService.getByItemId(orderItem.getItemId());
            itemService.update(item.getItemId(), item.getPrice().getPrice(), item.getStockQuantity() + orderQuantity);
        });
    }
}
