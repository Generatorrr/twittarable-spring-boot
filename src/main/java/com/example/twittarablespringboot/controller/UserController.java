package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.Role;
import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.repository.UserRepository;
import com.example.twittarablespringboot.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public String saveUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {

        userService.saveUser(username, form, user);
        
        return "redirect:/user";
    }
    
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }
    
    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }
}
