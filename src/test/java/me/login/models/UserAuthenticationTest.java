package me.login.models;

import me.login.services.UserAuthenticationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthenticationTest {
    @Autowired
    private UserAuthentication userAuthentication;

    @Test
    public void whenStatusIsSet_ItIsReadableAsEnumAlthoughConvertedToInt() {
        List<UserStatus> states = Arrays.asList(UserStatus.ACTIVE, UserStatus.LOCKED, UserStatus.UNAPPROVED);

        states.forEach(status -> {
            userAuthentication.setStatus(status);
            Assert.assertEquals(status, userAuthentication.getStatus());
        });
    }
}
