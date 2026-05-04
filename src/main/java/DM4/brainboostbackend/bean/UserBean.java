package DM4.brainboostbackend.bean;

import DM4.brainboostbackend.domain.UserEntity;

public record UserBean(Long id,
                       String username,
                       String password,
                       String firstName,
                       String lastName) {

    public static UserBean from(UserEntity userEntity) {
        return new UserBean(userEntity.getId(), userEntity.getUsername(), userEntity.getPasswordHash(), userEntity.getFirstName(), userEntity.getLastName());
    }
}

