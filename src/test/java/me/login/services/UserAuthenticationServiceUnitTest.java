package me.login.services;

import me.login.models.UserAuthentication;
import me.login.models.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthenticationServiceUnitTest {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<UserAuthentication> userAuthentications = userAuthenticationService.list();

        Assert.assertEquals(4, userAuthentications.size());
    }

    @Test
    public void whenApplicationStarts_thenStatusIsProperlyReadable() {
        List<UserAuthentication> userAuthentications = userAuthenticationService.list();

        Assert.assertEquals(UserStatus.ACTIVE, userAuthentications.get(0).getStatus());
        Assert.assertEquals(UserStatus.ACTIVE, userAuthentications.get(1).getStatus());
        Assert.assertEquals(UserStatus.LOCKED, userAuthentications.get(2).getStatus());
        Assert.assertEquals(UserStatus.UNAPPROVED, userAuthentications.get(3).getStatus());
    }

    @Test
    public void whenCreatingNewUserWhichExists_thenReturnsNull() {
        List<UserAuthentication> userAuthentications = userAuthenticationService.list();
        UserAuthentication userAuthentication = userAuthenticationService.registerNewUser("Matthias", "123");

        Assert.assertEquals(null, userAuthentication);
    }

    @Test
    public void whenCreatingNewUser_thenCountUpListAndCheckPasswordEncryption() {
        List<UserAuthentication> userAuthentications = userAuthenticationService.list();
        Assert.assertEquals(4, userAuthentications.size());
        String id = "Eggert";

        UserAuthentication userAuthentication = userAuthenticationService.registerNewUser(id, "123");

        userAuthentications = userAuthenticationService.list();
        Assert.assertEquals(4 + 1, userAuthentications.size());
        Assert.assertEquals(id, userAuthentications.get(4).getIdentification());

        String pwd = userAuthentications.get(4).getPassword();
        Assert.assertTrue(pwd.startsWith("$2a$10$")); // salt algorithm
        Assert.assertTrue(pwd.length() == 60); // hash + salt
    }
}
