package org.demo.common;

import java.time.LocalDate;

public class TimeCalculator {

    private TimeCalculator() {}

    // 이전 날짜 가져오기(daysAgo 만큼)
    public static LocalDate getDateDaysAgo(int daysAgo) {
        LocalDate nowDate = LocalDate.now();
        return nowDate.minusDays(daysAgo);
    }
}
