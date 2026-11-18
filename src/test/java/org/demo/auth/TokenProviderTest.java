package org.demo.auth;

import org.demo.auth.domain.TokenProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {

    private final String secretKey = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
    private TokenProvider tokenProvider = new TokenProvider(secretKey);

    @Test
    void givenValidUserAndRole_whenCreateToken_thenReturnValidToken() {
        //given
        Long userId = 1L;
        String role = "ADMIN";

        //when
        String token = tokenProvider.createToken(userId, role);

        //then
        assertNotNull(token);
        assertEquals(userId, tokenProvider.getUserId(token));
        assertEquals(role, tokenProvider.getUserRole(token));
    }

    @Test
    void givenInvalidToken_whenGetUserId_thenThrowError() {
        // given
        String invalidToken = "invalid_token";

        // when
        assertThrows(Exception.class, () -> tokenProvider.getUserId(invalidToken));
    }
}
