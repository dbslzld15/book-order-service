package org.prgrms.kdt.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class OrderCreateRequest {
    @NotNull(message = "주소는 필수로 입력해야합니다.")
    private String address;
    private List<OrderItemCreateRequest> orderItems;

}
