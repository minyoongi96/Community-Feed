package org.demo.common.ui;

import org.demo.common.domain.exception.ErrorCode;

public record Response<T>(Integer code, String msg, T value) {
    public static <T> Response<T> ok(T value) {
        return new Response<>(0, "ok", value);
    }

    public static <T> Response<T> error(ErrorCode e){
        return new Response<> (e.getCode(), e.getMsg(), null);
    }
}
