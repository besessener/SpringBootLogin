package me.login.repositories;

import me.login.models.IdentificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<IdentificationData, Long> {
    @Query(value = "SELECT * FROM identification_data WHERE status=0 AND identification=:#{#id}", nativeQuery = true)
    List<IdentificationData> findAllActiveUsersNative(@Param("id") String id);

    @Query(value = "SELECT * FROM identification_data WHERE identification=:#{#id}", nativeQuery = true)
    List<IdentificationData> findUsersNative(@Param("id") String id);
}
