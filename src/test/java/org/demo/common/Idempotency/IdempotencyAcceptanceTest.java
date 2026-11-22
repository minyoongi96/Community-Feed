package org.demo.common.Idempotency;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.demo.acceptance.steps.IdempotencySteps.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdempotencyAcceptanceTest {

    @Autowired
    private IdempotencyTestController idempotencyTestController;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
        resetExecuteCount();
    }

    @Test
    void givenIdempotencyKey_whenRequestWithSameIdempotencyKey_thenReturnBeforeSameResponse() {
        // given
        String key = UUID.randomUUID().toString();

        // when
        var firstResponse = requestIdempotency(key);
        var secondResponse = requestIdempotency(key);

        // then
        firstResponse.statusCode(200);
        secondResponse.statusCode(200);

        int firstValue = firstResponse.extract().jsonPath().getInt("value");
        int secondValue = secondResponse.extract().jsonPath().getInt("value");

        assertEquals(firstValue, secondValue);
        assertEquals(1, getExecuteCount());
    }

    @Test
    void givenTwoIdempotencyKeys_whenRequestWithAnotherTwoKeys_thenExecuteTwice() {
        // given
        String key1 = UUID.randomUUID().toString();
        String key2 = UUID.randomUUID().toString();

        // when
        var firstResponse = requestIdempotency(key1);
        var secondResponse = requestIdempotency(key2);

        // then
        firstResponse.statusCode(200);
        secondResponse.statusCode(200);

        assertEquals(2, getExecuteCount());
    }

    @Test
    void givenRequestIsProcessing_whenRequestWithSameIdempotencyKey_thenThrowException() throws Exception {
        // given
        String key = UUID.randomUUID().toString();

        // when
        CompletableFuture<ValidatableResponse> firstRequest =
                CompletableFuture.supplyAsync(() -> requestIdempotencyProcessing(key));

        // 첫 번째 요청이 실제 처리에 진입할 때까지 대기
        assertTrue(idempotencyTestController.awaitProcessingStarted());

        try {
            // 첫 번째 요청이 처리 중인 상태에서 동일한 키로 요청
            var secondResponse = requestIdempotencyProcessing(key);

            // then
            // 실제 GlobalExceptionHandler에서 설정한 HTTP 상태 코드에 맞춰 검증
            secondResponse.statusCode(409);

        } finally {
            // 첫 번째 요청이 종료될 수 있도록 허용
            idempotencyTestController.releaseProcessing();
        }

        // 첫 번째 요청은 정상 완료되어야 함
        firstRequest.get(5, TimeUnit.SECONDS)
                .statusCode(200);

        // 비즈니스 로직은 한 번만 실행되어야 함
        assertEquals(1, getExecuteCount());
    }
}
