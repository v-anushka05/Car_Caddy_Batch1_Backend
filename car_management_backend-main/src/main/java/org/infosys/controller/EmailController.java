package org.infosys.controller;

import org.infosys.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/send-email")
    public void sendEmailNotification(String toEmail,String subject ,String body){
         emailService.sendEmail(toEmail,subject,body);
    }
}
