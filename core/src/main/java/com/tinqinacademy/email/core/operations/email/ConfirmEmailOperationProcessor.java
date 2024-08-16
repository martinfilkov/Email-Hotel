package com.tinqinacademy.email.core.operations.email;

import com.tinqinacademy.email.api.operations.base.Errors;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailInput;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOperation;
import com.tinqinacademy.email.api.operations.email.confirm.ConfirmEmailOutput;
import com.tinqinacademy.email.api.operations.exceptions.NotFoundException;
import com.tinqinacademy.email.core.ErrorMapper;
import com.tinqinacademy.email.core.operations.BaseOperationProcessor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class ConfirmEmailOperationProcessor extends BaseOperationProcessor implements ConfirmEmailOperation {
    private final JavaMailSender emailSender;

    public ConfirmEmailOperationProcessor(ConversionService conversionService,
                                          Validator validator,
                                          ErrorMapper errorMapper,
                                          JavaMailSender emailSender) {
        super(conversionService, validator, errorMapper);
        this.emailSender = emailSender;
    }

    @Override
    public Either<Errors, ConfirmEmailOutput> process(ConfirmEmailInput input) {
        return validateInput(input)
                .flatMap(validated -> confirmEmail(input));
    }

    private Either<Errors, ConfirmEmailOutput> confirmEmail(ConfirmEmailInput input) {
        return Try.of(() -> {
                    log.info("Start confirmEmail with input: {}", input);
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(input.getEmailTo());
                    message.setSubject("Your Confirmation Code");
                    message.setText(String.format("Your confirmation code is: %s", input.getConfirmationCode()));
                    emailSender.send(message);
                    ConfirmEmailOutput output = ConfirmEmailOutput.builder().build();
                    log.info("End converting from confirmEmail with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
