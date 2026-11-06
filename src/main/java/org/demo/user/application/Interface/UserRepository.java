package org.demo.user.application.Interface;

import org.demo.user.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById (Long id);
}
