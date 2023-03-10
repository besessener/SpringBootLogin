package me.login.services;

import me.login.models.Book;
import me.login.repositories.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public List<Book> list() {
        return userAuthenticationRepository.findAll();
    }
}
