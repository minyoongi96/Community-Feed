package org.demo.common.Idempotency;

import org.demo.common.ui.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class IdempotencyTestController {

    private final AtomicInteger count = new AtomicInteger(0);

    private volatile CountDownLatch processingStarted = new CountDownLatch(1);
    private volatile CountDownLatch allowToFinish = new CountDownLatch(1);

    @PostMapping("/test/idempotency/reset")
    public Response<Void> reset() {
        count.set(0);
        processingStarted = new CountDownLatch(1);
        allowToFinish = new CountDownLatch(1);

        return Response.ok(null);
    }

    @Idempotent
    @PostMapping("/test/idempotency")
    public Response<Integer> test() {
        int result = count.incrementAndGet();
        return Response.ok(result);
    }

    @GetMapping("/test/idempotency/count")
    public Response<Integer> count() {
        return Response.ok(count.get());
    }

    @Idempotent
    @PostMapping("/test/idempotency/processing")
    public Response<Integer> processing() throws InterruptedException {
        int result = count.incrementAndGet();

        // 첫 번째 요청이 처리ㅐ 중
        processingStarted.countDown();

        if(!allowToFinish.await(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("테스트 대기 시간 초과");
        }

        return Response.ok(result);
    }

    public boolean awaitProcessingStarted() throws InterruptedException {
        return processingStarted.await(3, TimeUnit.SECONDS);
    }

    public void releaseProcessing() {
        allowToFinish.countDown();
    }
}
