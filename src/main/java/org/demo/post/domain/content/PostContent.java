package org.demo.post.domain.content;

public class PostContent extends Content {

    private static final int MIN_POST_LENHTH = 5;
    private static final int MAX_POST_LENHTH = 500;

    public PostContent(String content) {
        super(content);
    }

    @Override
    protected void checkText(String contentText) {
        if (contentText == null || contentText.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (contentText.length() > MAX_POST_LENHTH) {
            throw new IllegalArgumentException();
        }

        if (contentText.length() < MIN_POST_LENHTH) {
            throw new IllegalArgumentException();
        }
    }
}
