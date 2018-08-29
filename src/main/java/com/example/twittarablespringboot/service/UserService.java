package com.example.twittarablespringboot.service;

import com.example.twittarablespringboot.entity.Role;
import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    // In the moment @Autowired is not so cool to use for inject dependencies in needed class. Use constructor's field:)
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public UserService(UserRepository userRepository,MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }
    
    public boolean addUser(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(null != userFromDB) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Twittarable App. Please, to finish registration visit next link:\n" + 
                            "http://localhost:8080/activation/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
        
        return true;
        
    }

    public boolean activateUser(String code)
    {
        
        User user = userRepository.findByActivationCode(code);
        
        if (null == user) {
            return false;
        }
        
        user.setActivationCode(null);
        userRepository.save(user);
        
        return true;        
    }
}