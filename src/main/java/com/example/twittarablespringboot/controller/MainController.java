package com.example.twittarablespringboot.controller;

import com.example.twittarablespringboot.entity.Message;
import com.example.twittarablespringboot.entity.User;
import com.example.twittarablespringboot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Iterable<Message> messages;
        if (null != filter && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String addMessage
            (
                    @AuthenticationPrincipal User user,
                    @RequestParam String text,
                    @RequestParam String tag,
                    Model model,
                    @RequestParam("file") MultipartFile file
            ) throws IOException {
        Message message = new Message(text, tag, user);

        if (null != file) {
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename= uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

}
