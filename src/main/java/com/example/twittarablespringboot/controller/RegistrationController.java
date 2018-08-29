package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewAccount(User user, Model model) {

        if(!userService.addUser(user)) {
            model.addAttribute("userAlreadytExists", "User with the name has already exists!");
            return "registration";
        }
        
        return "redirect:/login";

    }
    
    @GetMapping("/activation/{code}")
    public String activateAccount(Model model, @PathVariable String code) {
        
        boolean isActivated = userService.activateUser(code);
        
        if (isActivated) {
            model.addAttribute("message", "User activated successfully!");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        
        return "login";
    }
    

}
