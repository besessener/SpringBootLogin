package me.login.services;

import me.login.models.IdentificationStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdentificationDataService identificationDataService;

    @Test
    public void whenAuthenticating_ThenFailIfUserNotFound() {
        AuthenticationService.AuthenticationStatus authenticationResult = authenticationService.authenticate("NotExistingUser", "whatever");
        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenFailIfPasswordNotMatching() {
        AuthenticationService.AuthenticationStatus authenticationResult = authenticationService.authenticate("Thomas", "saihttaM1!Typo");
        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenFailIfUserNotActive() {
        AuthenticationService.AuthenticationStatus authenticationResult = authenticationService.authenticate("Someone", "enoemoS1!");
        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenSucceedIfAllRulesWerePassing() {
        AuthenticationService.AuthenticationStatus authenticationResult = authenticationService.authenticate("Thomas", "saihttaM1!");
        Assert.assertEquals(AuthenticationService.AuthenticationStatus.AUTHENTICATED, authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenLockUserOnTypingWrongPassword3TimesAndResetCounterIfSuccessfullAuthenticated() {
        AuthenticationService.AuthenticationStatus authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(1), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(2), identificationDataService.list().get(0).getFailedLoginAttempts());


        authenticationResult = authenticationService.authenticate("Matthias", "saihttaM1!");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.AUTHENTICATED, authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(0), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(1), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD, authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(2), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertEquals(AuthenticationService.AuthenticationStatus.LOCKED, authenticationResult);
        Assert.assertEquals(IdentificationStatus.LOCKED, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(3), identificationDataService.list().get(0).getFailedLoginAttempts());
    }
}
