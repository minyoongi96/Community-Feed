package org.demo.acceptance.steps;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

public class IdempotencySteps {

    public static ValidatableResponse requestIdempotency(String key) {
        return RestAssured
                .given()
                .header("Idempotency-Key", key)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/test/idempotency")
                .then();
    }

    public static int getExecuteCount() {
        return RestAssured
                .given()
                .get("/test/idempotency/count")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("value");
    }

    public static void resetExecuteCount() {
        RestAssured
                .given()
                .post("/test/idempotency/reset")
                .then()
                .statusCode(200);
    }

    public static ValidatableResponse requestIdempotencyProcessing(String key) {
        return RestAssured
                .given()
                .header("Idempotency-Key", key)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/test/idempotency/processing")
                .then();
    }
}
