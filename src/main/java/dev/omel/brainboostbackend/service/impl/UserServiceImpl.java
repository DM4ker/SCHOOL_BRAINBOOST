package dev.omel.brainboostbackend.service.impl;

import dev.omel.brainboostbackend.bean.UserBean;
import dev.omel.brainboostbackend.service.UserService;
import dev.omel.brainboostbackend.worker.UserWorker;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserWorker userWorker;

    public UserServiceImpl(UserWorker userWorker) {
        this.userWorker = userWorker;
    }


    @Override
    public UserBean login(String username, String password) throws Exception {
        return userWorker.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public UserBean register(String username, String password, String firstName, String lastName) throws Exception {
        return userWorker.createUser(username, password, firstName, lastName);
    }
}
