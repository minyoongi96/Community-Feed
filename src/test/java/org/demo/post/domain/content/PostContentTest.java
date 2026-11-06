package org.demo.post.domain.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostContentTest {


    @Test
    void givenContentLengthIsOK_whenCreated_thenReturnTextContent() {
        // given
        String text = "this is a test";

        // when
        PostContent postContent = new PostContent(text);

        // then
        assertEquals(text, postContent.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenCreated_thenThrowsError() {
        // given
        String content = "a".repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @ValueSource(strings = {"텟", "갉", "싯", "슳","긁"})
    void givenContentLengthIsOverKorean_whenCreated_thenThrowsError(String koreanWord) {
        // given
        String content = koreanWord.repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @Test
    void givenContentLengthIsUnder_whenCreated_thenThrowsError() {
        // given
        String content = "a".repeat(4);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 500})
    void givenContentLengthIsAtBoundary_whenCreated_thenReturnTextContent(int length) {
        // given
        String content = "a".repeat(length);

        // when
        PostContent postContent = new PostContent(content);

        // then
        assertEquals(content, postContent.getContentText());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenCreated_thenThrowsError(String value) {

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(value));
    }

    @Test
    void givenContentLengthIsOK_whenUpdated_thenReturnTextContent() {
        // given
        String text = "this is a test";
        PostContent postContent = new PostContent(text);
        String updateText = "this is a update test";

        // when
        postContent.updateContent(updateText);

        // then
        assertEquals(updateText, postContent.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenUpdated_thenThrowsError() {
        // given
        String text = "this is a test";
        PostContent postContent = new PostContent(text);
        String updateText = "a".repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(updateText));
        assertEquals(text, postContent.getContentText());
    }

    @Test
    void givenContentLengthIsUnder_whenUpdated_thenThrowsError() {
        // given
        String text = "this is a test";
        PostContent postContent = new PostContent(text);
        String updateText = "a".repeat(4);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(updateText));
        assertEquals(text, postContent.getContentText());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 500})
    void givenContentLengthIsAtBoundary_whenUpdated_thenReturnTextContent(int length) {
        // given
        PostContent postContent = new PostContent("this is a test");
        String updateText = "a".repeat(length);

        // when
        postContent.updateContent(updateText);

        // then
        assertEquals(updateText, postContent.getContentText());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsEmpty_whenUpdated_thenThrowsError(String updateText) {
        // given
        String text = "this is a test";
        PostContent postContent = new PostContent(text);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(updateText));
        assertEquals(text, postContent.getContentText());
    }
}
