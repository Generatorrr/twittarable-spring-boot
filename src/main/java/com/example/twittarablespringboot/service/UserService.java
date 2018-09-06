package com.example.twittarablespringboot.service;

import com.example.twittarablespringboot.entity.Role;
import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    // In the moment @Autowired is not so cool to use for inject dependencies in needed class. Use constructor's field:)
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            MailSenderService mailSenderService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!!!");
        }
        return user;
    }
    
    public boolean addUser(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(null != userFromDB) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendEmail(user);

        return true;
        
    }

    private void sendEmail(User user)
    {
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
    }

    public boolean activateUser(String code)
    {
        
        User user = userRepository.findByActivationCode(code);
        
        if (null == user) {
            return false;
        }
        
        user.setActivationCode(null);
        user.setPassword2(user.getPassword());
        userRepository.save(user);
        
        return true;        
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(String username, Map<String, String> form, User user)
    {

        Set<String> roles = Arrays
                .stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        user.setUsername(username);
        userRepository.save(user);
        
    }

    public void updateProfile(User user, String password, String email)
    {
        
        String userCurrentEmail = user.getEmail();
        
        boolean isEmailChanged = (email != null && !email.equals(userCurrentEmail)) 
                || (userCurrentEmail != null && !userCurrentEmail.equals(email));
        
        if (isEmailChanged) {
            user.setEmail(email);
            
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        
        userRepository.save(user);

        if (isEmailChanged) {
            sendEmail(user);
        }
    }
}
