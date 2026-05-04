package DM4.brainboostbackend.service.impl;

import DM4.brainboostbackend.bean.UserBean;
import DM4.brainboostbackend.service.UserService;
import DM4.brainboostbackend.worker.UserWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserWorker userWorker;

    public UserServiceImpl(UserWorker userWorker) {
        this.userWorker = userWorker;
    }


    @Override
    @Transactional
    public UserBean login(String username, String password) throws Exception {
        return userWorker.getUserByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public UserBean register(String username, String password, String firstName, String lastName) throws Exception {
        return userWorker.createUser(username, password, firstName, lastName);
    }

    @Override
    @Transactional
    public UserBean update(Long id, UserBean userBean) throws Exception {
        return userWorker.updateUser(id, userBean);
    }
}

