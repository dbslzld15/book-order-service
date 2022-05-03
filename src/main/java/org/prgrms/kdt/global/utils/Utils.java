package org.prgrms.kdt.global.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Utils {

    private Utils() {
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
