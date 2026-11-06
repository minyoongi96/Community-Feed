package org.demo.post.domain.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatetimeInfoTest {

    @Test
    void givenCreated_whenUpdated_thenTimeAndStateIsUpdated() {

        // given
        DatetimeInfo dateTimeInfo = new DatetimeInfo();
        LocalDateTime beforeDateTime = dateTimeInfo.getDateTime();

        // when
        dateTimeInfo.updateEditDatetime();

        // then
        assertTrue(dateTimeInfo.isEdited());
        assertNotEquals(beforeDateTime, dateTimeInfo.getDateTime());
    }
}
