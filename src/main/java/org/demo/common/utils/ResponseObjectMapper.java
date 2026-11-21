package org.demo.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.common.ui.Response;

public class ResponseObjectMapper {

    private ResponseObjectMapper() {}

    private static final ObjectMapper objMapper = new ObjectMapper();

    // Response -> String 형태
    public static Response toResponseObject(String response) {
        try {
            return objMapper.readValue(response, Response.class);
        } catch (Exception e) {
            return null;
        }
    }

    // String 형태 -> Response
    public static String toStringResponse(Response<?> response) {
        try {
            return objMapper.writeValueAsString(response);
        } catch (Exception e) {
            return null;
        }
    }
}
