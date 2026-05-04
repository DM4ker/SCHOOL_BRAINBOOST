package dev.omel.brainboostbackend.worker;

import dev.omel.brainboostbackend.bean.UserBean;
import dev.omel.brainboostbackend.domain.UserEntity;
import dev.omel.brainboostbackend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;

@Component
public class UserWorker {

    private final UserRepository userRepository;

    public UserWorker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBean createUser(String username, String password, String firstName, String lastName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        //should normally be done in the Frontend for man in the middle attack prevention :)
        //Should be Hashed normally with Salt and Pepper as well
        //For production use Argon2 or PBKDF2 those are more secure than plain hashing
        //NEVER USE BASE64 FOR PASSWORD HASHING/ENCRYPTION IN PRODUCTION
        String hashedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        userEntity.setPasswordHash(hashedPassword);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userRepository.save(userEntity);
        return UserBean.from(userEntity);
    }

    public UserBean getUserByUsernameAndPassword(String username, String password) {
        //should normally be done in the Frontend for man in the middle attack prevention :)
        //Should be Hashed normally with Salt and Pepper as well
        //For production use Argon2 or PBKDF2
        //NEVER USE BASE64 FOR PASSWORD HASHING/ENCRYPTION IN PRODUCTION

        String passwordHash = Base64.getEncoder().encodeToString(password.getBytes());
        return UserBean.from(Objects.requireNonNull(userRepository.findByUsernameAndPasswordHash(username, passwordHash).orElseThrow()));
    }
}
