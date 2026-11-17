package org.demo.acceptance.steps;

import io.restassured.RestAssured;
import org.demo.post.application.dto.CreateCommentRequestDto;
import org.demo.post.application.dto.CreatePostRequestDto;
import org.demo.post.ui.dto.GetContentResponseDto;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

public class FeedAcceptanceSteps {

    public static Long requestCreatePost(CreatePostRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/post")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("value", Long.class);
    }

    public static List<GetPostContentResponseDto> requestFeed(Long userId) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed/{userId}", userId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("value", GetPostContentResponseDto.class);
    }

    public static Long requestCreateComment(CreateCommentRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/comment")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("value", Long.class);
    }

    public static List<GetContentResponseDto> requestComment(Long userId, Long postId) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/feed/{userId}/{postId}", userId, postId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList("value", GetContentResponseDto.class);
    }
}
