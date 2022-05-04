package org.prgrms.kdt.global.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class BaseEntity {
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public BaseEntity(LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
