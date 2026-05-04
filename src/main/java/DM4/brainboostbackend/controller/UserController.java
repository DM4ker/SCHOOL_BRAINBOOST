package DM4.brainboostbackend.controller;

import DM4.brainboostbackend.bean.LoginBean;
import DM4.brainboostbackend.bean.LoginResponse;
import DM4.brainboostbackend.bean.UserBean;
import DM4.brainboostbackend.config.JwtUtil;
import DM4.brainboostbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost"}, allowCredentials = "true")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginBean loginBean) throws Exception {
        UserBean userBean = userService.login(loginBean.username(), loginBean.password());
        UserBean sanitizedUserBean = new UserBean(userBean.id(), userBean.username(), null, userBean.firstName(), userBean.lastName());
        String token = jwtUtil.generateToken(userBean.username());
        LoginResponse response = new LoginResponse(token, sanitizedUserBean);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserBean> registerUser(@RequestBody UserBean userBean) throws Exception {
        UserBean responseUserBean = userService.register(userBean.username(), userBean.password(), userBean.firstName(), userBean.lastName());
        UserBean sanitizedUserBean = new UserBean(responseUserBean.id(), responseUserBean.username(), null, responseUserBean.firstName(), responseUserBean.lastName());
        return ResponseEntity.ok(sanitizedUserBean);
    }

    @PatchMapping("/user/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<LoginResponse> updateUser(@PathVariable Long id, @RequestBody UserBean userBean) throws Exception {
        UserBean updatedUserBean = userService.update(id, userBean);
        UserBean sanitizedUserBean = new UserBean(updatedUserBean.id(), updatedUserBean.username(), null, updatedUserBean.firstName(), updatedUserBean.lastName());
        String token = jwtUtil.generateToken(updatedUserBean.username());
        return ResponseEntity.ok(new LoginResponse(token, sanitizedUserBean));
    }

}

