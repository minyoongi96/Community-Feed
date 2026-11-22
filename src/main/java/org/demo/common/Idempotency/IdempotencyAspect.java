package org.demo.common.Idempotency;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.demo.common.ui.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {
    private final IdempotencyRepository idempotencyRepository;
    private final HttpServletRequest request;

    @Around("@annotation(Idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint) throws Throwable {
        String idempotencyKey = request.getHeader("Idempotency-Key");
        if(idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("Idempotency-Key 헤더가 필요합니다.");
        }

        // API 경로와 HTTP Method를 포함하여 멱등키 조합
        String key = request.getMethod() + ":" + request.getRequestURI() + ":" + idempotencyKey;

        // 요청별 소유권 식별자
        String token = UUID.randomUUID().toString();

        // 1. Redis에 멱등키 선점
        boolean acquired = idempotencyRepository.acquire(key, token);

        // 2. 동일한 요청이 들어오면
        if(!acquired) {
            Idempotency existing = idempotencyRepository.getByKey(key);
            // 처리 완료된 기존 응답 반환
            if(existing != null) {
                return existing.getResponse();
            }
            // 아직 처리 중인 요청
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 처리 중인 요청입니다.");
        }

        try{
            // 3. 비즈니스 로직 실행
            Object result = joinPoint.proceed();

            // 4. 응담 저장
            if(result instanceof Response<?> response) {
                idempotencyRepository.save(new Idempotency(key, response));
            } else {
                throw new IllegalStateException("멱등성 적용 메서드는 Response를 반환해야 합니다.");
            }

            return result;
        } catch (Throwable e) {
            // 5. 예외 처리 : Redis 키 삭제
            idempotencyRepository.release(key, token);
            throw e;
        }
    }
}
