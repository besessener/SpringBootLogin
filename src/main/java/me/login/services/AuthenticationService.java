package me.login.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.login.models.IdentificationData;
import me.login.models.IdentificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IdentificationDataService identificationDataService;

    public static final int MAX_ATTEMPT = 3;

    public enum AuthenticationStatus {
        AUTHENTICATED,
        NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD,
        LOCKED
    }

    public AuthenticationStatus authenticate(String identification, String password) {
        AuthenticationStatus result = AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD;
        List<IdentificationData> identificationDataList = identificationDataService.findAllActiveUsersNative(identification);

        if (!identificationDataList.isEmpty()) {
            IdentificationData identificationData =  identificationDataList.get(0);
            boolean passwordMatching = passwordEncoder.matches(password, identificationData.getPassword());
            if (passwordMatching) {
                result = AuthenticationStatus.AUTHENTICATED;
                identificationData.setFailedLoginAttempts(0);
            } else {
                identificationData.setFailedLoginAttempts(identificationData.getFailedLoginAttempts() + 1);
                if (MAX_ATTEMPT <= identificationData.getFailedLoginAttempts()) {
                    result = AuthenticationStatus.LOCKED;
                    identificationData.setStatus(IdentificationStatus.LOCKED);
                }
            }

            identificationDataService.save(identificationData);
        }

        return result;
    }
}
