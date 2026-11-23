package org.demo.auth.application.dto;

public record LoginRequestDto(String email, String password, String fcmToken) {
}
