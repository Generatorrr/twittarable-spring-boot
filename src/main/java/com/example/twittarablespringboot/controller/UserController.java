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

import javax.swing.text.html.HTMLDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value="User API", description="User operations")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @ApiOperation(value = "Get specific user", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @ApiOperation(value = "Change user data", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
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

    @ApiOperation(value = "Get yourself profile", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @ApiOperation(value = "Get yourself profile", response = HTMLDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "Forbidden")
        }
    )
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
