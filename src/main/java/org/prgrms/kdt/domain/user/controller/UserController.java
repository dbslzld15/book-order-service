package org.prgrms.kdt.domain.user.controller;

import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.entity.UserRole;
import org.prgrms.kdt.domain.user.request.UserCreateRequest;
import org.prgrms.kdt.domain.user.request.UserLoginRequest;
import org.prgrms.kdt.domain.user.request.UserPwResetRequest;
import org.prgrms.kdt.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String userLoginPage() {
        return "users/login";
    }

    @GetMapping("/join")
    public String userJoinPage() {
        return "users/join";
    }

    @GetMapping("/reset")
    public String userPwResetPage() {
        return "users/reset";
    }

    @PostMapping("/join")
    public void joinUser(@Valid UserCreateRequest createRequest) {
        userService.save(createRequest);
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginRequest loginRequest, HttpSession session) {
        User user = userService.getUserByLogin(loginRequest);
        session.setAttribute("userId", user.getUserId());
        if(user.getUserRole() == UserRole.USER) {
            return "redirect:/books";
        } else {
            return "admin";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String resetPassword(@Valid UserPwResetRequest resetRequest){
        userService.updatePassword(resetRequest);
        return "redirect:/";
    }
}
