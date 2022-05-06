package org.prgrms.kdt.domain.book.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter @Setter
public class BookCreateRequest {
    @NotBlank(message = "도서명은 필수 입력입니다.")
    private String title;
    @NotBlank(message = "저자명은 필수 입력입니다.")
    private String authorName;
    @NotNull(message = "금액은 필수 입력입니다.")
    @Positive(message = "금액은 음수가 될 수 없습니다.")
    private long price;
    @NotNull(message = "재고수량은 필수 입력입니다.")
    @Positive(message = "재고수량은 음수가 될 수 없습니다.")
    private int stockQuantity;
}
