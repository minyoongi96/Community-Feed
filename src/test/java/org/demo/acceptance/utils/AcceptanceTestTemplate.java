package org.demo.acceptance.utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    // 이메일로 토큰 가져오기
    protected String getEmailToken(String email) {
        return loader.getEmailToken(email);
    }

    // 검증된 이메일인지 확인
    protected boolean isEmailVerified(String email) {
        return loader.isEmailVerified(email);
    }
}
