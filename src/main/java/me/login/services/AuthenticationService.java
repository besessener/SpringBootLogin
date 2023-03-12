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

    @PersistenceContext
    private EntityManager entityManager;

    public static final int MAX_ATTEMPT = 3;

    public enum AuthenticationStatus {
        AUTHENTICATED,
        NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD,
        LOCKED
    }

    @Transactional
    public AuthenticationStatus authenticate(String identification, String password) {
        AuthenticationStatus result = AuthenticationStatus.NO_ACTIVE_IDENTIFICATION_OR_WRONG_PASSWORD;
        List<IdentificationData> identificationDataList = identificationDataService.list().stream().filter(entry ->
                entry.getIdentification().equals(identification)
                && entry.getStatus().equals(IdentificationStatus.ACTIVE)).collect(Collectors.toList());

        if (!identificationDataList.isEmpty()) {
            IdentificationData entry =  identificationDataList.get(0);
            boolean passwordMatching = passwordEncoder.matches(password, entry.getPassword());
            if (passwordMatching) {
                result = AuthenticationStatus.AUTHENTICATED;
                entry.setFailedLoginAttempts(0);
            } else {
                entry.setFailedLoginAttempts(entry.getFailedLoginAttempts() + 1);
                if (MAX_ATTEMPT <= entry.getFailedLoginAttempts()) {
                    result = AuthenticationStatus.LOCKED;
                    entry.setStatus(IdentificationStatus.LOCKED);
                }

                entityManager.persist(entry);
            }
        }

        return result;
    }
}
