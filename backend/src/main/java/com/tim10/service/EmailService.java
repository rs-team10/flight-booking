package com.tim10.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Reservation;
import com.tim10.domain.Seat;

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
    
    @Async
	@Transactional(readOnly = true, propagation=Propagation.MANDATORY)
    public void sendFlightReservationEmail(Reservation reservation, String emailAddress,String departure, String destination, String departureDate, Seat reservedSeat) throws MessagingException {
    	
    	MimeMessage mailMessage = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
    	
    	String body = "Hello " + reservation.getRegisteredUser().getFirstName() + 
        		"! You have successfully booked a flight from " + departure + " to " + destination +
        		" on " + departureDate + ". Your seat is " + reservedSeat.getRed() + (char)(reservedSeat.getKolona() + 64) +
        		". Enjoy your flight!";
    	
    	mailMessage.setContent(body, "text/html");
    	helper.setTo(emailAddress);
    	helper.setSubject("Flight successfully booked");
    	helper.setFrom("flightbooking.tim10@gmail.com");

        mailSender.send(mailMessage);
    }
    
    @Async
	@Transactional(readOnly = true, propagation=Propagation.MANDATORY)
    public void sendInvitationEmail(Reservation reservation, String emailAddress, String invitationCode, String departure, String destination, String departureDate, Seat reservedSeat) throws MessagingException {
    	
    	MimeMessage mailMessage = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
    	
    	String body = "Hello " + reservation.getFlightReservation().getPassengerFirstName() + 
        		"! You have been invited to travel with your friends from " + departure + " to " + destination +
        		" on " + departureDate + ". Your seat is " + reservedSeat.getRed() + (char)(reservedSeat.getKolona() + 64) +
        		". <a href='http://localhost:8080/#/userProfile'>Visit your profile</a> to accept the invitation";
    	
    	mailMessage.setContent(body, "text/html");
    	helper.setTo(emailAddress);
    	helper.setSubject("You have been invited on a trip!");
    	helper.setFrom("flightbooking.tim10@gmail.com");
      
        mailSender.send(mailMessage);
    }
}