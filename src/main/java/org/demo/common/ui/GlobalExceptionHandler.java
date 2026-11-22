package org.demo.common.ui;

import lombok.extern.slf4j.Slf4j;
import org.demo.common.domain.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException e){
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }

    // HTTP 상태 코드가 지정된 예외 처리
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<Void>> handleResponseStatusException(
            ResponseStatusException e
    ) {
        log.error(e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(Response.error(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e){
        log.error(e.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }
}
