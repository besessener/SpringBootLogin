package me.login.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import me.login.models.IdentificationData;
import me.login.models.IdentificationDataDto;
import me.login.models.IdentificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdentificationDataService identificationDataService;

    @Autowired
    private Validator validator;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Set<ConstraintViolation<IdentificationDataDto>> registerNewUser(String identification, String password, String passwordConfirmed) {
        IdentificationDataDto identificationDataDto = new IdentificationDataDto(
                identification, password, passwordConfirmed, IdentificationStatus.UNAPPROVED);
        boolean userExists = identificationDataService.list().stream()
                .anyMatch(user -> user.getIdentification().equalsIgnoreCase(identificationDataDto.getIdentification()));
        if (userExists) {
            return null;
        }

        Set<ConstraintViolation<IdentificationDataDto>> violations = validator.validate(identificationDataDto);
        if (violations.isEmpty()) {
            IdentificationData identificationData = new IdentificationData();
            identificationData.setIdentification(identificationDataDto.getIdentification());
            identificationData.setPassword(passwordEncoder.encode(identificationDataDto.getPassword()));
            identificationData.setStatus(identificationDataDto.getStatus());
            entityManager.persist(identificationData);
        }

        return violations;
    }
}
