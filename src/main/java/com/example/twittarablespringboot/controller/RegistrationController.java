package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.entity.Role;
import com.example.twittarablespringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewAccount(User user, Map<String, Object> model) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(null != userFromDB) {
            model.put("accountExists", "User with the name has already exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";

    }

}
