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

        Assert.assertEquals(userAuthentications.size(), 4);
    }

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords_StatusIsProperlyReadable() {
        List<UserAuthentication> userAuthentications = userAuthenticationService.list();

        Assert.assertEquals(userAuthentications.get(0).getStatus(), UserStatus.ACTIVE);
        Assert.assertEquals(userAuthentications.get(1).getStatus(), UserStatus.ACTIVE);
        Assert.assertEquals(userAuthentications.get(2).getStatus(), UserStatus.LOCKED);
        Assert.assertEquals(userAuthentications.get(3).getStatus(), UserStatus.UNAPPROVED);
    }
}
