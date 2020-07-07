package com.wildcodeschool.mailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/email")
    public String email(@RequestParam String to,
                        @RequestParam String subject,
                        @RequestParam String content) {

        try {
            sendEmail(to, subject, content);
        } catch (MailException | MessagingException exception) {
            return exception.getMessage();
        }
        return "ok";
    }

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String to, String subject, String content) throws MailException, MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, false);

        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText("<h1>" + content + "</h1><br><a href=\"#lol\">Se désabonner</a>", true);

        javaMailSender.send(msg);
    }
}
