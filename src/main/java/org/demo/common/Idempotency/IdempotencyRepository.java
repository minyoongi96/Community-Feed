package org.demo.common.Idempotency;

public interface IdempotencyRepository {

    // 멱등키 선점 (SET NX)
    boolean acquire(String key, String token);

    // 처리 완료된 멱등키 조회
    Idempotency getByKey(String key);

    // 처리 완료 결과 저장
    void save(Idempotency idempotency);

    // 처리 중 예외 발생 시 본인이 선점한 키만 삭제
    void release(String key, String token);
}
