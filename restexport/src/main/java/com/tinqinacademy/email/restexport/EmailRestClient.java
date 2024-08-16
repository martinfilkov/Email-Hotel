package com.tinqinacademy.email.restexport;

import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailInput;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOutput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordEmailInput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordEmailOutput;
import feign.Headers;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface EmailRestClient {
    @RequestLine("POST /api/email/confirm")
    ConfirmEmailOutput confirmEmail(ConfirmEmailInput request);

    @RequestLine("POST /api/email/recover")
    RecoverPasswordEmailOutput recoverPassword(RecoverPasswordEmailInput request);
}
