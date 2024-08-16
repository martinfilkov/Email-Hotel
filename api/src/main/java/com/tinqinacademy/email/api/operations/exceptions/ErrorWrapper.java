package com.tinqinacademy.email.api.operations.exceptions;

import com.tinqinacademy.email.api.operations.base.Errors;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ErrorWrapper implements Errors {
    private List<ErrorResponse> errors;
    private Date timestamp;
    private Integer code;
}
