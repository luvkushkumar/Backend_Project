package com.kushal.BackendApp.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body)
    {
        try
        {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("luvkushsharma1975@gmail.com");
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);

            log.info("message are sent..........");
        }
        catch (Exception e)
        {
            log.error("some error is there......");
        }

    }



}
