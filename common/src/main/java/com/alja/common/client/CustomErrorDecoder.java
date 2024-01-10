package com.alja.common.client;

import com.alja.errors.PhysicianError;
import com.alja.exception.PhysicianException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {
//fixme FIX ERROR HANDLING!
    private static final String ERROR_NAME = "errorName";
    private static final String EXCEPTION_NAME = "exceptionName";

    @Override
    public Exception decode(String methodKey, Response response) {

        String responseBody = getResponseBody(response);
        Map<String, String> responseValues = extractValues(responseBody);
        String errorName = responseValues.get(ERROR_NAME);
        String exceptionName = responseValues.get(EXCEPTION_NAME);
        if (errorName.isEmpty() || exceptionName.isEmpty()){
            throw new RuntimeException();
        }
        //todo error factory ? to retrieve required error
        return throwAppropriateException(methodKey, response, exceptionName, errorName);


    }

    private Exception throwAppropriateException(String methodKey, Response response, String exceptionName, String errorName) {
        if (exceptionName.equals(PhysicianException.class.getSimpleName())) {
            return new PhysicianException(PhysicianError.valueOf(errorName));
        }
        // todo Default behavior for other errors
        return defaultDecoder().decode(methodKey, response);
    }

    public static Map<String, String> extractValues(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            result.put(ERROR_NAME, jsonNode.path(ERROR_NAME).asText());
            result.put(EXCEPTION_NAME, jsonNode.path(EXCEPTION_NAME).asText());
        } catch (IOException e) {
            e.printStackTrace(); // todo Handle the exception appropriately
        }
        return result;
    }

    private String getResponseBody(Response response) {
        try {
            return StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading response body", e);
        }
    }

    private ErrorDecoder defaultDecoder() {
        return new Default();
    }
}
