package me.login.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.login.configs.EqualPassword;
import me.login.configs.ValidPassword;

@Getter
@AllArgsConstructor
@EqualPassword
public class IdentificationDataDto {

    @NotNull
    @Size(min = 3, max = 30)
    private String identification;

    @NotNull
    @ValidPassword
    public String password;

    @NotNull
    @ValidPassword
    public String passwordConfirmed;

    private IdentificationStatus status;
}
