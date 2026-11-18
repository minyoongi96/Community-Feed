package org.demo.auth;

import org.demo.auth.domain.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PasswordTest {

    @Test
    void givenPassword_thenMatchSamePassword_thenReturnTrue() {
        Password password = Password.createEncryptPassword("password");

        assertTrue(password.matchPassword("password"));
    }

    @Test
    void givenPassword_thenMatchDiffPassword_thenReturnFalse() {
        Password password = Password.createEncryptPassword("1234");

        assertFalse(password.matchPassword("password"));
    }

    @Test
    void givenPasswordIsNull_thenThrowError() {
        assertThrows(IllegalArgumentException.class, () -> Password.createEncryptPassword(null));
    }
}
