package org.prgrms.kdt.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.domain.order.entity.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class OrderUpdateRequest {
    @NotNull(message = "주소는 필수로 입력해야합니다.")
    private String address;
    @NotNull(message = "주문상태는 필수로 입력해야합니다.")
    private OrderStatus orderStatus;
    @NotNull(message = "주문 날짜는 필수로 입력해야합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderDateTime;
    @NotNull(message = "사용자의 ID는 필수로 입력해야합니다.")
    private long userId;
}
