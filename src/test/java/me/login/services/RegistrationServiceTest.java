package me.login.services;

import jakarta.validation.ConstraintViolation;
import me.login.models.IdentificationData;
import me.login.models.IdentificationDataDto;
import me.login.models.IdentificationStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationServiceTest {

    @Autowired
    private IdentificationDataService identificationDataService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenCreatingNewUserWhichExists_thenReturnsNull() {
        List<IdentificationData> identificationData = identificationDataService.list();
        Set<ConstraintViolation<IdentificationDataDto>> violations = registrationService
                .registerNewUser("Matthias", "123", "123");

        Assert.assertEquals(null, violations);
    }

    @Test
    public void whenCreatingNewUser_thenCountUpListAndCheckPasswordEncryption() {
        List<IdentificationData> identificationData = identificationDataService.list();
        Assert.assertEquals(4, identificationData.size());
        String id = "Eggert";

        String rawPwd = "MyPa$$W0rt#1.";
        registrationService.registerNewUser(id, rawPwd, rawPwd);

        identificationData = identificationDataService.list();
        Assert.assertEquals(4 + 1, identificationData.size());
        Assert.assertEquals(id, identificationData.get(4).getIdentification());

        String encodedPwd = identificationData.get(4).getPassword();
        Assert.assertTrue(encodedPwd.startsWith("$2a$10$")); // salt algorithm
        Assert.assertTrue(encodedPwd.length() == 60); // hash + salt
        Assert.assertTrue(passwordEncoder.matches(rawPwd, encodedPwd));
    }

    @Test
    public void whenCreatingNewUser_thenItMustBeInactiveInitially() {
        List<IdentificationData> identificationData = identificationDataService.list();
        String id = "NewUser";
        String rawPwd = "MyPa$$W0rt#1.";
        registrationService.registerNewUser(id, rawPwd, rawPwd);

        identificationData = identificationDataService.list();
        Assert.assertEquals(6, identificationData.size());
        Assert.assertEquals(id, identificationData.get(5).getIdentification());
        Assert.assertEquals(IdentificationStatus.UNAPPROVED, identificationData.get(5).getStatus()); // salt algorithm

    }
}
