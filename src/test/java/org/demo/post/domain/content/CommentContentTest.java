package org.demo.post.domain.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentContentTest {

    @Test
    void givenContentLengthIsOK_whenCreated_thenReturnTextContent() {
        // given
        String text = "this is a test";

        // when
        CommentContent commentContent = new CommentContent(text);

        // then
        assertEquals(text, commentContent.getContentText());
    }

    @Test
    void givenContentLengthIsOver_whenCreated_thenThrowsError() {
        // given
        String content = "a".repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @ValueSource(strings = {"텟", "갉", "싯", "슳","긁"})
    void givenContentLengthIsOverKorean_whenCreated_thenThrowsError(String koreanWord) {
        // given
        String content = koreanWord.repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentIsNullOrEmpty_whenCreated_thenThrowsError(String content) {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }
}
