package DM4.brainboostbackend.service;

import DM4.brainboostbackend.bean.UserBean;

public interface UserService {

    UserBean login(String username, String password) throws Exception;

    UserBean register(String username, String password, String firstName, String lastName) throws Exception;

    UserBean update(Long id, UserBean userBean) throws Exception;
}

