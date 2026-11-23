package org.demo.auth.application;

import lombok.RequiredArgsConstructor;
import org.demo.auth.application.dto.CreateUserAuthRequestDto;
import org.demo.auth.application.dto.LoginRequestDto;
import org.demo.auth.application.dto.UserAccessTokenResponseDto;
import org.demo.auth.application.interfaces.EmailVerificationRepository;
import org.demo.auth.application.interfaces.UserAuthRepository;
import org.demo.auth.domain.Email;
import org.demo.auth.domain.TokenProvider;
import org.demo.auth.domain.UserAuth;
import org.demo.auth.repository.jpa.JpaUserAuthRepository;
import org.demo.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final TokenProvider tokenProvider;

    public Long registerUser(CreateUserAuthRequestDto dto) {
        // 인증된 이메일인지 먼저 확인
        Email email = Email.createEmail(dto.email());
        if(!emailVerificationRepository.isEmailVerified(email)) {
            throw new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());
        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }

    public UserAccessTokenResponseDto login(LoginRequestDto dto) {
        UserAuth userAuth = userAuthRepository.loginUser(dto.email(), dto.password(), dto.fcmToken());
        String token = tokenProvider.createToken(userAuth.getUserId(), userAuth.getUserRole());

        return new UserAccessTokenResponseDto(token);
    }
}
