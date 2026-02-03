package com.learnmap.be.domain.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {
    public static final String ZONE_UTC = "UTC";

    public static final String ZONE_VN = "Asia/Ho_Chi_Minh";


    public static Long getNowMillisAtUtc() {
        return LocalDateTime.now(ZoneId.of(ZONE_UTC)).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static Instant getNow(){
        return LocalDateTime.now(ZoneId.of(ZONE_UTC)).toInstant(ZoneOffset.UTC);
    }

}
