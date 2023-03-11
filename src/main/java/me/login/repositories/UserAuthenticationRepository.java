package me.login.repositories;

import me.login.models.IdentificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<IdentificationData, Long> {

}
