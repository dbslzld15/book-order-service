package org.prgrms.kdt.domain.book.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.book.vo.Price;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;

import java.time.LocalDateTime;

@Getter
public class Book extends Item {
    private Long bookId;
    private Title title;
    private Name authorName;

    @Builder
    public Book(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                Long itemId, Price price, int stockQuantity, Long bookId, Title title, Name authorName) {
        super(createdDateTime, modifiedDateTime, itemId, price, stockQuantity);
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
    }
}
