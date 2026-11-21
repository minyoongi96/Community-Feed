package org.demo.common.Idempotency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.demo.common.ui.Response;

@Getter
@AllArgsConstructor
public class Idempotency {

    private final String key;
    private final Response<?> response;
}
