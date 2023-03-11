package me.login.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.login.models.IdentificationData;
import me.login.models.UserStatus;
import me.login.repositories.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationDataService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public List<IdentificationData> list() {
        return userAuthenticationRepository.findAll();
    }
}
