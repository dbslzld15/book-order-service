package org.prgrms.kdt.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.order.exception.OrderException;
import org.prgrms.kdt.domain.user.vo.Address;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.prgrms.kdt.domain.order.entity.OrderStatus.*;
import static org.prgrms.kdt.domain.order.exception.OrderExceptionType.*;

@Getter
public class Order extends BaseEntity {
    private Long orderId;
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime orderDateTime;
    private long userId;

    @Builder
    public Order(Long orderId, Address address, OrderStatus orderStatus,
                 LocalDateTime orderDateTime, long userId) {
        super(now(), now());
        this.orderId = orderId;
        this.address = address;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.userId = userId;
    }

    public void cancelOrder() {
        if(orderStatus != ACCEPTED){
            throw new OrderException(ORDER_CAN_NOT_CANCELED);
        }
        orderStatus = CANCELED;
        setModifiedDateTime(now());
    }
}
