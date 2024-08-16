package com.tinqinacademy.email.api.operations.email.confirm;

import com.tinqinacademy.email.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ConfirmEmailInput implements OperationInput {
    @NotBlank(message = "Email to cannot be blank")
    private String emailTo;

    @NotBlank(message = "Confirmation code cannot be blank")
    private String confirmationCode;
}
