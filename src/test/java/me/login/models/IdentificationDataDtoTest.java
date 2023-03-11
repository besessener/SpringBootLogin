package me.login.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentificationDataDtoTest {

    @Autowired
    private Validator validator;
    @Test
    public void whenNewIdentificationDataDtoIsCreated_thenInvalidPasswordShallFail() {
        String pwd = "tooEasy";
        IdentificationDataDto invalidIdentificationData = new IdentificationDataDto(
                "someone", pwd, pwd, IdentificationStatus.UNAPPROVED);

        Set<ConstraintViolation<IdentificationDataDto>> violations = validator.validate(invalidIdentificationData);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void whenNewIdentificationDataDtoIsCreated_thenValidPasswordShallSucceed() {
        String pwd = "ThisIsAllowedWith135#%$";
        IdentificationDataDto invalidIdentificationData = new IdentificationDataDto(
                "someone", pwd, pwd, IdentificationStatus.UNAPPROVED);

        Set<ConstraintViolation<IdentificationDataDto>> violations = validator.validate(invalidIdentificationData);
        Assert.assertTrue(violations.isEmpty());
    }
}
