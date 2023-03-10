package me.login.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Entity
@Component
public class UserAuthentication {

    @Id
    @GeneratedValue
    private Long id; // key
    private String identification; // username or whatever
    private String password;
    private int status;

    public UserStatus getStatus() {
        return UserStatus.values()[this.status];
    }

    public void setStatus(UserStatus status) {
        this.status = status.ordinal();
    }
}
