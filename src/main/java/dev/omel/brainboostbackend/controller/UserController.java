package dev.omel.brainboostbackend.controller;

import dev.omel.brainboostbackend.bean.LoginBean;
import dev.omel.brainboostbackend.bean.UserBean;
import dev.omel.brainboostbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserBean> loginUser(@RequestBody LoginBean loginBean) throws Exception {
        UserBean userBean = userService.login(loginBean.username(), loginBean.password());
        UserBean sanitizedUserBean = new UserBean(userBean.id(), userBean.username(), null, userBean.firstName(), userBean.lastName());
        return ResponseEntity.ok(sanitizedUserBean);
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserBean> registerUser(@RequestBody UserBean userBean) throws Exception {
        UserBean responseUserBean = userService.register(userBean.username(), userBean.password(), userBean.firstName(), userBean.lastName());
        UserBean sanitizedUserBean = new UserBean(responseUserBean.id(), responseUserBean.username(), null, responseUserBean.firstName(), responseUserBean.lastName());
        return ResponseEntity.ok(sanitizedUserBean);
    }

}
