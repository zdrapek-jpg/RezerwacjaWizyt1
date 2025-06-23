package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.User;
import com.example.RezerwacjaWizyt1.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("/register")
    public String regiserUser(@Valid User user, BindingResult result,Model model){
        if (result.hasErrors()){
            return "register";
        }
        userService.register(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }



}
