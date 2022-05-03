package org.prgrms.kdt.domain.book.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class Book extends BaseEntity {
    private Long bookId;
    private Title title;
    private Name authorName;
    private Long itemId;

    @Builder
    public Book(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime, Long bookId, Title title, Name authorName, Long itemId) {
        super(createdDateTime, modifiedDateTime);
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
        this.itemId = itemId;
    }
}
