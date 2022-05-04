package org.prgrms.kdt.global.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public BaseEntity(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
