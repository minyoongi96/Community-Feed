package org.demo.message.application.interfaces;

import org.demo.user.domain.User;

public interface MessageRepository {
    void sendLikeMessage(User sender, User targetUser);
}
