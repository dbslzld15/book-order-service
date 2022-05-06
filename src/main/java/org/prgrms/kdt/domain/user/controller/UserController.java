package org.prgrms.kdt.domain.user.controller;

import org.prgrms.kdt.domain.user.entity.User;
import org.prgrms.kdt.domain.user.request.UserCreateRequest;
import org.prgrms.kdt.domain.user.request.UserLoginRequest;
import org.prgrms.kdt.domain.user.request.UserPwResetRequest;
import org.prgrms.kdt.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String userJoinPage(Model model) {
        model.addAttribute("createRequest", new UserCreateRequest());
        return "users/join";
    }

    @GetMapping("/pwreset")
    public String userPwResetPage() {
        return "users/pwreset";
    }

    @PostMapping("/join")
    public String joinUser(@ModelAttribute("createRequest") @Valid UserCreateRequest createRequest,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return"/users/join";
        }
        userService.save(createRequest);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") @Valid UserLoginRequest loginRequest,
                        BindingResult bindingResult, HttpSession session) {
        if(bindingResult.hasErrors()) {
            return "/users/login";
        }
        User user = userService.getUserByLogin(loginRequest);
        session.setAttribute("userId", user.getUserId());
        return "menu";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users";
    }

    @PostMapping("/reset")
    public String resetPassword(@Valid UserPwResetRequest resetRequest){
        userService.updatePassword(resetRequest);
        return "redirect:/users";
    }
}
