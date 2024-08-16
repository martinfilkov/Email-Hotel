package com.tinqinacademy.email.restexport;

import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailInput;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOutput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordInput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordOutput;
import feign.Headers;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface EmailRestClient {
    @RequestLine("POST /api/email/confirm")
    ConfirmEmailOutput confirmEmail(ConfirmEmailInput request);

    @RequestLine("POST /api/email/recover")
    RecoverPasswordOutput recoverPassword(RecoverPasswordInput request);
}
