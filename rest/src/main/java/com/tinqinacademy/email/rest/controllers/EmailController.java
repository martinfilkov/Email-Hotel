package com.tinqinacademy.email.rest.controllers;

import com.tinqinacademy.email.api.operations.base.EmailMappings;
import com.tinqinacademy.email.api.operations.base.Errors;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailInput;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOperation;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOutput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordInput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordOperation;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordOutput;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController extends BaseController {
    private final ConfirmEmailOperation confirmEmailOperation;
    private final RecoverPasswordOperation recoverPasswordOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully sent confirm email"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(value = EmailMappings.CONFIRM_EMAIL)
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmEmailInput input) {
        Either<Errors, ConfirmEmailOutput> output = confirmEmailOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully sent confirm email"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(EmailMappings.RECOVER_PASSWORD)
    public ResponseEntity<?> recoverPassword(@RequestBody RecoverPasswordInput input) {
        Either<Errors, RecoverPasswordOutput> output = recoverPasswordOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }
}
