package org.prgrms.kdt.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.global.model.BaseEntity;

import static java.time.LocalDateTime.now;

@Getter
public class OrderItem extends BaseEntity {
    private long orderItemId;
    private Price totalPrice;
    private int orderQuantity;
    private long orderId;
    private long itemId;
    private static final int MIN_ORDER_QUANTITY = 1;

    @Builder
    public OrderItem(long orderItemId, Price totalPrice, int orderQuantity, long orderId, long itemId) {
        super(now(), now());
        validateOrderQuantity(orderQuantity);
        this.orderItemId = orderItemId;
        this.totalPrice = totalPrice;
        this.orderQuantity = orderQuantity;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    private void validateOrderQuantity(int orderQuantity) {
        if(orderQuantity < MIN_ORDER_QUANTITY) {
            throw new IllegalArgumentException("주문수량은 1개 미만이 될 수 없습니다");
        }
    }
}
