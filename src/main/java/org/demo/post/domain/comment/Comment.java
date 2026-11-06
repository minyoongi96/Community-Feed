package org.demo.post.domain.comment;

import org.demo.common.domain.PositiveIntegerCounter;
import org.demo.post.domain.Post;
import org.demo.post.domain.content.Content;
import org.demo.user.domain.User;

public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final Content content;
    private final PositiveIntegerCounter likeCount;

    public Comment(Long id, Post post, User author, Content content) {
        if (author == null) {
            throw new IllegalArgumentException();
        }

        if (post == null) {
            throw new IllegalArgumentException();
        }

        if (content == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCounter();
    }

    public void like(User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.likeCount.increase();
    }

    public void unlike(User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.likeCount.decrease();
    }

    public void updateComment(User author, String updateContent) {
        if(!this.author.equals(author)) {
            throw new IllegalArgumentException();
        }

        this.content.updateContent(updateContent);
    }

    public String getContent() {
        return content.getContentText();
    }

    public int getLikeCount() {
        return likeCount.getCount();
    }
}
