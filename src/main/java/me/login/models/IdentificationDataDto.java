package me.login.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.login.configs.ValidPassword;

@Getter
@AllArgsConstructor
public class IdentificationDataDto {

    private String identification;

    @ValidPassword
    public String password;

    @ValidPassword
    public String passwordConfirmed;

    private IdentificationStatus status;
}
