package me.login.services;

import me.login.models.UserAuthentication;
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
        Assert.assertEquals(userAuthentications.size(), 3);
    }
}
