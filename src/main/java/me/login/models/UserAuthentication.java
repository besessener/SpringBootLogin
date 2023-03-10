package me.login.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Entity
@Component
public class UserAuthentication {

    @Id
    @GeneratedValue
    private Long id; // key
    private String identification; // username or whatever
    private String password;

    @Getter
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
}
