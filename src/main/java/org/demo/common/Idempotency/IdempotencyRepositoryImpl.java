package org.demo.common.Idempotency;

import lombok.RequiredArgsConstructor;
import org.demo.common.utils.ResponseObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IdempotencyRepositoryImpl implements IdempotencyRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "idempotency:";
    private static final String PROCESSING = "PROCESSING:";
    private static final String COMPLETED = "COMPLETED:";

    private static final Duration PROCESSING_TTL = Duration.ofMinutes(5);
    private static final Duration COMPLETED_TTL = Duration.ofHours(24);

    private static final DefaultRedisScript<Long> RELEASE_SCRIPT =
            new DefaultRedisScript<>(
                    """
                            if redis.call('GET', KEYS[1]) == ARGV[1] then
                                return redis.call('DEL', KEYS[1])
                            end
                            return 0
                            """,
                    Long.class
            );

    // Redis 키 선점
    @Override
    public boolean acquire(String key, String token) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(
                PREFIX + key,
                PROCESSING + token,
                PROCESSING_TTL
        );

        return Boolean.TRUE.equals(result);
    }

    // 기존 요청의 처리 결과 조회
    @Override
    public Idempotency getByKey(String key) {
        String value = redisTemplate.opsForValue().get(PREFIX + key);

        // 키가 존재 아예 없거나 아직 처리 중인 요청일 때
        if (value == null || !value.startsWith(COMPLETED)) {
            return null;
        }

        String response = value.substring(COMPLETED.length());
        return new Idempotency(key, ResponseObjectMapper.toResponseObject(response));
    }

    @Override
    public void save(Idempotency idempotency) {
        String response = ResponseObjectMapper.toStringResponse(idempotency.getResponse());

        redisTemplate.opsForValue().set(
                PREFIX + idempotency.getKey(),
                COMPLETED + response,
                COMPLETED_TTL
        );
    }

    @Override
    public void release(String key, String token) {
        redisTemplate.execute(
                RELEASE_SCRIPT,
                List.of(PREFIX + key),
                PROCESSING + token);
    }
}
