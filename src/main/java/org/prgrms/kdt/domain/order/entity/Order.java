package org.prgrms.kdt.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.order.vo.OrderStatus;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class Order extends BaseEntity {
    private long orderId;
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private long userId;

    @Builder
    public Order(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                 long orderId, Address address, OrderStatus orderStatus, LocalDateTime orderDate, long userId) {
        super(createdDateTime, modifiedDateTime);
        this.orderId = orderId;
        this.address = address;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.userId = userId;
    }

    @Builder
    public Order(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                 Address address, OrderStatus orderStatus, LocalDateTime orderDate, long userId) {
        super(createdDateTime, modifiedDateTime);
        this.address = address;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.userId = userId;
    }
}
