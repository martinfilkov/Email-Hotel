package com.tinqinacademy.email.core.operations.email;

import com.tinqinacademy.email.api.operations.base.Errors;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordEmailInput;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordEmailOperation;
import com.tinqinacademy.email.api.operations.email.recover.RecoverPasswordEmailOutput;
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

@Slf4j
@Service
public class RecoverPasswordOperationProcessor extends BaseOperationProcessor implements RecoverPasswordEmailOperation {
    private final JavaMailSender emailSender;

    public RecoverPasswordOperationProcessor(ConversionService conversionService,
                                             Validator validator,
                                             ErrorMapper errorMapper,
                                             JavaMailSender emailSender) {
        super(conversionService, validator, errorMapper);
        this.emailSender = emailSender;
    }

    @Override
    public Either<Errors, RecoverPasswordEmailOutput> process(RecoverPasswordEmailInput input) {
        return validateInput(input)
                .flatMap(validated -> recoverPassword(input));
    }

    private Either<Errors, RecoverPasswordEmailOutput> recoverPassword(RecoverPasswordEmailInput input) {
        return Try.of(() -> {
                    log.info("Start confirmEmail with input: {}", input);
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(input.getEmailTo());
                    message.setSubject("Password Recovery");
                    message.setText(String.format("Your new password is: %s\nPlease change it after logging in.", input.getNewPassword()));
                    emailSender.send(message);
                    RecoverPasswordEmailOutput output = RecoverPasswordEmailOutput.builder().build();
                    log.info("End converting from confirmEmail with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
