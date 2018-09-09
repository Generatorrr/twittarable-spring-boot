package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.entity.dto.CaptchaResponseDto;
import com.example.twittarablespringboot.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.HTMLDocument;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Controller
@Api(value="Registration API", description="Registrations/activations operations")
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?captchaSecret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String captchaSecret;

    private final UserService userService;

    private final RestTemplate restTemplate;

    public RegistrationController(UserService userService, RestTemplate restTemplate)
    {
        this.userService = userService;
        this.restTemplate = restTemplate;
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
    public String addNewAccount(
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {

        String captchaUrl = String.format(CAPTCHA_URL, captchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(captchaUrl, Collections.emptyList(), CaptchaResponseDto.class);
        boolean isCaptchaFails = null != response && !response.isSuccess();

        if (isCaptchaFails) {
            model.addAttribute("captchaError", "Fill the captcha, please.");
        }

        boolean isPasswordConfirmationEmpty = StringUtils.isEmpty(passwordConfirmation);
        if (isPasswordConfirmationEmpty) {
            model.addAttribute("password2Error", "Password confirmation field cannot be empty");
        }
        if (null != user.getPassword() && user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if (bindingResult.hasErrors() || isCaptchaFails || isPasswordConfirmationEmpty) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "registration";
        }
        if(!userService.addUser(user)) {
            model.addAttribute("usernameError", "User with the name has already exists!");
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
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Activation code is not found!");
            model.addAttribute("messageType", "danger");
        }
        
        return "login";
    }
    

}
