package org.prgrms.kdt.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter @Setter
public class OrderItemCreateRequest {
    @NotNull(message = "상품 ID값이 입력되지 않았습니다.")
    private long itemId;
    @NotNull(message = "총 주문 금액은 필수 입력입니다.")
    @Positive(message = "총 주문 금액은 음수가 될 수 없습니다.")
    private long totalPrice;
    @NotNull(message = "주문 수량은 필수 입력입니다.")
    @Positive(message = "주문 수량은 음수가 될 수 없습니다.")
    private int orderQuantity;
}
