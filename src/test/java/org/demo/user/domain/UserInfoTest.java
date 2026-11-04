package org.demo.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserInfoTest {

    @Test
    void givenNameAndProfileImageUrl_whenCreated_thenThrowNothing() {
        // given
        String name = "test1";
        String profileImageUrl = "";

        // when
        // then
        assertDoesNotThrow(() -> new UserInfo(name, profileImageUrl));
    }

    @Test
    void givenBlankNameAndProfileImageUrl_whenCreated_thenThrowError() {
        // given
        String name = "";
        String profileImageUrl = "";

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new UserInfo(name, profileImageUrl));
    }
}
