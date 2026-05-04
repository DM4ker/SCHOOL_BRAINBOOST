package dev.omel.brainboostbackend.service;

import dev.omel.brainboostbackend.bean.UserBean;

public interface UserService {

    UserBean login(String username, String password) throws Exception;

    UserBean register(String username, String password, String firstName, String lastName) throws Exception;
}
