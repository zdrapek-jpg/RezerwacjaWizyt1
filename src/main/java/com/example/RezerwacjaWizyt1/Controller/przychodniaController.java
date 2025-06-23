package com.example.RezerwacjaWizyt1.Controller;

import com.example.RezerwacjaWizyt1.Entity.User;
import com.example.RezerwacjaWizyt1.Services.przychodniaService;
import com.example.RezerwacjaWizyt1.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/rezerwacjawizyt")
public class przychodniaController {
    private final przychodniaService rezerwacjaWizyt;
    private final UserService userService;

    public przychodniaController(przychodniaService rezerwacjaWizyt, UserService userService) {
        this.rezerwacjaWizyt = rezerwacjaWizyt;
        this.userService = userService;
    }
    @GetMapping
    public String listrezerwacjaWizyt(Model model , Principal principal){

        String email  = principal.getName();
        User user =userService.findByEmail(email).orElse(null);
        //model.addAttribute("wizyty",RezerwacjaWizytService.findByUser());
        return "przychodnia";

    }
}
