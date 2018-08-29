/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.twittarablespringboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * TODO: write a brief summary fragment.
 * <p>
 * TODO: write a detailed description.
 * <p>
 * Created on 8/29/18.
 * <p>
 * @author vmartinkov
 */

@Service
public class MailSenderService
{

    @Value("${spring.mail.username}")
    private String username;
    
    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }
    
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        
        mail.setFrom(username);
        mail.setTo(emailTo);
        mail.setSubject(subject);
        mail.setText(message);
        
        mailSender.send(mail);
    }
}
