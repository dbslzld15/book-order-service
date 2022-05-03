package org.prgrms.kdt.domain.book.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class Item extends BaseEntity {
    private Long itemId;
    private Price price;
    private int stockQuantity;
    private static final int MIN_STOCK_QUANTITY = 0;

    @Builder
    public Item(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime, long itemId, Price price, int stockQuantity) {
        super(createdDateTime, modifiedDateTime);
        validateStockQuantity(stockQuantity);
        this.itemId = itemId;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    private void validateStockQuantity(int stockQuantity) {
        if(stockQuantity < MIN_STOCK_QUANTITY) {
            throw new IllegalArgumentException("재고 수량은 0개 미만이 될 수 없습니다.");
        }
    }
}
