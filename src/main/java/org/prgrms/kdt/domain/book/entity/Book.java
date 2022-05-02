package org.prgrms.kdt.domain.book.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.kdt.domain.book.vo.Title;
import org.prgrms.kdt.domain.user.vo.Name;
import org.prgrms.kdt.global.model.BaseEntity;

import java.time.LocalDateTime;

@Getter
public class Book extends BaseEntity {
    private long bookId;
    private Title title;
    private Name authorName;

    @Builder
    public Book(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                long bookId, Title title, Name authorName) {
        super(createdDateTime, modifiedDateTime);
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
    }

    @Builder
    public Book(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime, Title title, Name authorName) {
        super(createdDateTime, modifiedDateTime);
        this.title = title;
        this.authorName = authorName;
    }
}
