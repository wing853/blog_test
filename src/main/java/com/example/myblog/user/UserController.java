package com.example.myblog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService us;



    @GetMapping("/login-form")
    public String loginView() {

        return "user/login-form";
    }

    @PostMapping("/login")
    public String loginProc(UserRequest.LoginDTO loginDTO,HttpSession session) {
        loginDTO.validate();
        UserResponse.SessionDTO sessionDTO = us.login(loginDTO);
        session.setAttribute("sessionUser",sessionDTO);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/join-form")
    public String joinFormPage() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String joinProc(UserRequest.JoinDTO joinDTO){
        joinDTO.validate();
        us.join(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateFormPage(HttpSession session, Model model) {
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        UserResponse.SessionDTO sessionDTO = us.checkUser(sessionUser.getId());
        model.addAttribute("user",sessionDTO);
        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String updateProc(UserRequest.UpdateDTO updateDTO, HttpSession session){
        updateDTO.validate();
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        us.update(sessionUser.getId(),updateDTO,session);
        return "redirect:/";
    }
}
