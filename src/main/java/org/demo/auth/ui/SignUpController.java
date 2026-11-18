package org.demo.auth.ui;

import lombok.RequiredArgsConstructor;
import org.demo.auth.application.EmailService;
import org.demo.auth.application.dto.CreateUserAuthRequestDto;
import org.demo.auth.application.dto.SendEmailRequestDto;
import org.demo.common.ui.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final EmailService emailService;

    @PostMapping("/send-verification-email")
    public Response<Void> sendEmail(@RequestBody SendEmailRequestDto dto) {
        emailService.sendEmail(dto);
        return Response.ok(null);
    }

    @GetMapping("/verify-token")
    public Response<Void> verifyEmail(String email, String token) {
        emailService.verifyEmail(email, token);
        return Response.ok(null);
    }

    @PostMapping("/register")
    public Response<Long> registaer(@RequestBody CreateUserAuthRequestDto dto) {
        return Response.ok(1L);
    }
}
