package me.login.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.login.models.IdentificationData;
import me.login.models.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdentificationDataService identificationDataService;

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public IdentificationData registerNewUser(String identification, String password) {
        boolean userExists = identificationDataService.list().stream()
                .anyMatch(user -> user.getIdentification().equalsIgnoreCase(identification));
        if (!userExists) {
            IdentificationData identificationData = new IdentificationData();
            identificationData.setIdentification(identification);
            identificationData.setPassword(passwordEncoder.encode(password));
            identificationData.setStatus(UserStatus.UNAPPROVED);
            entityManager.persist(identificationData);
            return identificationData;
        }

        return null;
    }
}
