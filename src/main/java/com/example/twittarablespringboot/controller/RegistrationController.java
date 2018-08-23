package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.Account;
import com.example.twittarablespringboot.entity.Role;
import com.example.twittarablespringboot.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewAccount(Account account, Map<String, Object> model) {

        Account accountFromDB = accountRepository.findByUsername(account.getUsername());

        if(null != accountFromDB) {
            model.put("accountExists", "User with the name has already exists!");
            return "registration";
        }

        account.setActive(true);
        account.setRoles(Collections.singleton(Role.USER));
        accountRepository.save(account);

        return "redirect:/login";

    }

}
