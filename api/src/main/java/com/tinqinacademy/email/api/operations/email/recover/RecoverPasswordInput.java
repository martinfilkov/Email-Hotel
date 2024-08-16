package com.tinqinacademy.email.api.operations.email.recover;

import com.tinqinacademy.email.api.operations.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RecoverPasswordInput implements OperationInput {
    @NotBlank(message = "Email to cannot be blank")
    private String emailTo;

    @NotBlank(message = "New password cannot be blank")
    private String newPassword;
}
