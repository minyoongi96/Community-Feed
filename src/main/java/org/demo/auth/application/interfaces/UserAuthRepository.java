package org.demo.auth.application.interfaces;

import org.demo.auth.domain.UserAuth;
import org.demo.user.domain.User;

public interface UserAuthRepository {

    UserAuth registerUser (UserAuth auth, User user);
    UserAuth loginUser(String email, String password, String fcmToken);
}
