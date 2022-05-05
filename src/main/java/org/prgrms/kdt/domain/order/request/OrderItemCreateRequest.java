package org.prgrms.kdt.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class OrderItemCreateRequest {
    private long itemId;
    private long totalPrice;
    private int orderQuantity;
}
