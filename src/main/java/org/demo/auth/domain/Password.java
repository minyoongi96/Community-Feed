package org.demo.auth.domain;

public class Password {

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    // 암호화된 패스워드 생성
    public static Password createEncryptPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 빈값이 될 수 없습니다.");
        }
        return new Password(SHA256.encrypt(password));
    }

    // 문자열 패스워드 생성
    public static Password createPassword(String password){
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 빈값이 될 수 없습니다.");
        }
        return new Password(password);
    }

    public boolean matchPassword(String password) {
        return encryptedPassword.matches(SHA256.encrypt(password));
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
