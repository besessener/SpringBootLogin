package me.login.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Component
@Getter @Setter
public class IdentificationData {
    @Id
    private String identification;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
}
