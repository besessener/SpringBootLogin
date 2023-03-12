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
        boolean authenticationResult = authenticationService.authenticate("NotExistingUser", "whatever");
        Assert.assertFalse(authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenFailIfPasswordNotMatching() {
        boolean authenticationResult = authenticationService.authenticate("Thomas", "saihttaM1!Typo");
        Assert.assertFalse(authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenFailIfUserNotActive() {
        boolean authenticationResult = authenticationService.authenticate("Someone", "enoemoS1!");
        Assert.assertFalse(authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenSucceedIfAllRulesWerePassing() {
        boolean authenticationResult = authenticationService.authenticate("Thomas", "saihttaM1!");
        Assert.assertTrue(authenticationResult);
    }

    @Test
    public void whenAuthenticating_ThenLockUserOnTypingWrongPassword3Times() {
        boolean authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertFalse(authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(1), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertFalse(authenticationResult);
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(2), identificationDataService.list().get(0).getFailedLoginAttempts());

        authenticationResult = authenticationService.authenticate("Matthias", "wrongPassword");

        Assert.assertFalse(authenticationResult);
        Assert.assertEquals(IdentificationStatus.LOCKED, identificationDataService.list().get(0).getStatus());
        Assert.assertEquals(Integer.valueOf(3), identificationDataService.list().get(0).getFailedLoginAttempts());
    }
}
