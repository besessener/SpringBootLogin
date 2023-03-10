package me.login.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.login.models.UserAuthentication;
import me.login.models.UserStatus;
import me.login.repositories.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserAuthentication> list() {
        return userAuthenticationRepository.findAll();
    }

    @Transactional
    public UserAuthentication registerNewUser(String identification, String password) {
        boolean userExists = this.list().stream().anyMatch(user -> user.getIdentification().equals(identification));
        if (!userExists) {
            UserAuthentication userAuthentication = new UserAuthentication();
            userAuthentication.setIdentification(identification);
            userAuthentication.setPassword(passwordEncoder.encode(password));
            userAuthentication.setStatus(UserStatus.UNAPPROVED);
            entityManager.persist(userAuthentication);
            return userAuthentication;
        }

        return null;
    }
}
