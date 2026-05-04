package DM4.brainboostbackend.repository;

import DM4.brainboostbackend.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndPasswordHash(String username, String passwordHash);

    Optional<UserEntity> findByUsername(String username);
}

