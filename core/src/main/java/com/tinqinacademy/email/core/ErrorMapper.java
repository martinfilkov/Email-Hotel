package com.tinqinacademy.email.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.email.api.operations.base.OperationInput;
import com.tinqinacademy.email.api.operations.exceptions.ErrorResponse;
import com.tinqinacademy.email.api.operations.exceptions.ErrorWrapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ErrorMapper {
    public ErrorWrapper handleError(Throwable ex, HttpStatusCode statusCode) {
        return ErrorWrapper
                .builder()
                .errors(List.of(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .build()))
                .code(statusCode.value())
                .build();
    }

    public ErrorWrapper handleViolations(Set<ConstraintViolation<OperationInput>> violations, HttpStatusCode statusCode) {
        List<ErrorResponse> responses = violations.stream()
                .map(v -> ErrorResponse.builder()
                        .message(v.getMessage())
                        .build())
                .toList();

        return ErrorWrapper.builder()
                .errors(responses)
                .code(statusCode.value())
                .build();
    }

    public ErrorWrapper handleFeignException(FeignException ex) {
        try {
            String errorBody = ex.contentUTF8();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode errorNode = objectMapper.readTree(errorBody);
            return objectMapper.treeToValue(errorNode, ErrorWrapper.class);
        } catch (Exception e) {
            return ErrorWrapper
                    .builder()
                    .errors(List.of(ErrorResponse.builder()
                            .message("An error occurred while processing your request")
                            .build()))
                    .code(ex.status())
                    .build();
        }
    }
}
