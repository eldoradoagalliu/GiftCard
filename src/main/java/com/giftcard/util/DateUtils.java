package com.giftcard.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.giftcard.constant.ApplicationConstants.TIRANE_ZONE_ID;

public class DateUtils {
    public static Date getLocalDateTime(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(TIRANE_ZONE_ID));
        return Date.from(zonedDateTime.toInstant());
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");
        return formatter.format(date);
    }
}
