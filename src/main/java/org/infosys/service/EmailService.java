package org.infosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
    	System.out.println(to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);message.setSubject(subject);message.setText(body);
        mailSender.send(message);
        System.out.println("Email sent successfully to " + to);
    }

} 