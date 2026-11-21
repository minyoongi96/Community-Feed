package org.demo.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeCalculator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeCalculator() {}

    // 이전 날짜 가져오기(daysAgo 만큼)
    public static LocalDate getDateDaysAgo(int daysAgo) {
        LocalDate nowDate = LocalDate.now();
        return nowDate.minusDays(daysAgo);
    }

    // LocalDateTime 형식 변환
    public static String getFormattedDate(LocalDateTime dateTime) {
        if(dateTime == null) {
            return "";
        }
        return dateTime.format(FORMATTER);
    }
}
