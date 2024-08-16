package com.tinqinacademy.email.api.operations.exceptions;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ErrorResponse {
    private String message;
}
