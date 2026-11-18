package org.demo.acceptance.auth;

import org.demo.acceptance.utils.AcceptanceTestTemplate;
import org.demo.auth.application.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.demo.acceptance.steps.LoginAcceptanceSteps.requestLoginGetResponseCode;
import static org.demo.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "user1@test.com";

    @BeforeEach
    void setUp() {
        this.cleanUp();
        this.createUser(email);
    }

    @Test
    void givenEmailAndPassword_whenLogin_thenReturnToken() {

        // given
        LoginRequestDto dto = new LoginRequestDto(email, "password");

        // when
        String token = requestLoginGetToken(dto);

        // then
        assertNotNull(token);
    }

    @Test
    void givenEmailAndWrongPassword_whenLogin_thenReturnCodeNotZero() {
        // given
        LoginRequestDto dto = new LoginRequestDto(email, "wrong_password");

        // when
        Integer code = requestLoginGetResponseCode(dto);

        // then
        assertEquals(400, code);
    }
}
