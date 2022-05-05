package org.prgrms.kdt.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class OrderCreateRequest {
    private String address;
    private List<OrderItemCreateRequest> orderItemsRequest;

}
