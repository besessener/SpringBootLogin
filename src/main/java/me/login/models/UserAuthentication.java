package me.login.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserAuthentication {

    @Id
    @GeneratedValue
    private Long id; // key
    private String identification; // username or whatever
    private String password;
}