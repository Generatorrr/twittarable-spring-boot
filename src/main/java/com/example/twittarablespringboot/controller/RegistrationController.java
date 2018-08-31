package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.HTMLDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value="Registration API", description="Registrations/activations operations")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService)
    {
        this.userService = userService;
    }

    @ApiOperation(value = "Get registration page", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return needed page")
        }
    )
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @ApiOperation(value = "Register new user", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Register successfull"),
            @ApiResponse(code = 400, message = "Bad request")
        }
    )
    @PostMapping("/registration")
    public String addNewAccount(User user, Model model) {

        if(!userService.addUser(user)) {
            model.addAttribute("userAlreadytExists", "User with the name has already exists!");
            return "registration";
        }
        
        return "redirect:/login";

    }

    @ApiOperation(value = "Activate user", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User activated successfully"),
            @ApiResponse(code = 200, message = "Activation code is not found!")
        }
    )
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
