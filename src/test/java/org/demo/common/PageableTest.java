package org.demo.common;

import org.demo.common.domain.Pageable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageableTest {

    @Test
    void givenPageableIndexIsNull_whenGetOffset_thenShouldBeReturn0() {
        // given
        Pageable pageable = new Pageable();

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        // then
        assertEquals(0, offset);
        assertEquals(10, limit);
    }

    @Test
    void givenPageableIndexIs2Size5_whenGetOffset_thenShouldBe5() {
        // given
        Pageable pageable = new Pageable(2, 5);

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        // then
        assertEquals(5, offset);
        assertEquals(5, limit);
    }
}

