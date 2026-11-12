package org.demo.user.application.Interface;

import org.demo.user.domain.User;

public interface UserRepository {
    User save(User user);

    User findById (Long id);
}
