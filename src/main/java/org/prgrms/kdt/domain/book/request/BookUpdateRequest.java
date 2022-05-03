package org.prgrms.kdt.domain.book.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Getter @Setter
public class BookUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String authorName;
    @NotNull
    @Positive
    private long price;
    @NotNull
    @Positive
    private int stockQuantity;
}
