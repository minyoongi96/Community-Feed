package org.demo.acceptance.utils;

import io.restassured.RestAssured;
import org.demo.auth.application.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.demo.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTestTemplate {

    static {
        RestAssured.port = 8088;
    }

    @Autowired
    private DatabaseCleanUp cleanUp;

    @Autowired
    private DataLoader loader;

    @BeforeEach
    public void init() {
        cleanUp.execute();
        loader.loadData();
    }

    protected void cleanUp() {
        cleanUp.execute();
    }

    // community_email_verification 테이블에서 이메일로 토큰 가져오기
    protected String getEmailToken(String email) {
        return loader.getEmailToken(email);
    }

    // 검증된 이메일인지 확인
    protected boolean isEmailVerified(String email) {
        return loader.isEmailVerified(email);
    }

    // user_auth 테이블에서 email로 userId 가져오기
    protected Long getUserId(String email) {
        return loader.getUserId(email);
    }

    protected void createUser(String email) {
        loader.createUser(email);
    }

    protected String login(String email) {
        return requestLoginGetToken(new LoginRequestDto(email, "password", "fcmToken"));
    }
}
