package me.login.services;

import me.login.models.UserAuthentication;
import me.login.repositories.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public List<UserAuthentication> list() {
        return userAuthenticationRepository.findAll();
    }
}
