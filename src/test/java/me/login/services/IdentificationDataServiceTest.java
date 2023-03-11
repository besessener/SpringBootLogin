package me.login.services;

import me.login.models.IdentificationData;
import me.login.models.IdentificationStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentificationDataServiceTest {

    @Autowired
    private IdentificationDataService identificationDataService;

    @Test
    public void whenApplicationStarts_thenStatusIsProperlyReadable() {
        List<IdentificationData> identificationData = identificationDataService.list();

        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationData.get(0).getStatus());
        Assert.assertEquals(IdentificationStatus.ACTIVE, identificationData.get(1).getStatus());
        Assert.assertEquals(IdentificationStatus.LOCKED, identificationData.get(2).getStatus());
        Assert.assertEquals(IdentificationStatus.UNAPPROVED, identificationData.get(3).getStatus());
    }

    @Test
    public void whenApplicationStarts_thenInitialPasswordsMatchCorrectlyWithoutAdditionalEncryption() {
        List<IdentificationData> identificationData = identificationDataService.list();

        Assert.assertEquals("saihttaM1!", identificationData.get(0).getPassword());
        Assert.assertEquals("samohT1!", identificationData.get(1).getPassword());
        Assert.assertEquals("enoemoS1!", identificationData.get(2).getPassword());
        Assert.assertEquals("eslE-enoemoS1!", identificationData.get(3).getPassword());
    }
}
