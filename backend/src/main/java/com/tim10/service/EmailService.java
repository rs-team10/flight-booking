package com.tim10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tim10.domain.RegisteredUser;

@Service("emailService")
public class EmailService {


	@Autowired
	private JavaMailSender mailSender;
	
    @Async
    public void sendEmail(RegisteredUser user) {
    	
    	
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("flightbooking.tim10@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8081/auth/"+ user.getVerificationCode());
    	
        mailSender.send(mailMessage);
    }
}