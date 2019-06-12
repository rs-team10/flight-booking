package com.tim10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public void sendFlightReservationEmail(Reservation reservation, String emailAddress,String departure, String destination, String departureDate, Seat reservedSeat) {
    	
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Flight successfully booked");
        mailMessage.setFrom("flightbooking.tim10@gmail.com");
        mailMessage.setText("Hello " + reservation.getRegisteredUser().getFirstName() + 
        		"! You have successfully booked a flight from " + departure + " to " + destination +
        		" on " + departureDate + ". Your seat is " + reservedSeat.getRed() + (char)(reservedSeat.getKolona() + 64) +
        		". Enjoy your flight!");
    	
        try {
        	mailSender.send(mailMessage);
        } catch (MailException e) {
        	e.printStackTrace();
        }
    }
    
    @Async
    public void sendInvitationEmail(Reservation reservation, String emailAddress, String invitationCode, String departure, String destination, String departureDate, Seat reservedSeat) {
    	
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("You have been invited on a trip!");
        mailMessage.setFrom("flightbooking.tim10@gmail.com");
        mailMessage.setText("Hello " + reservation.getFlightReservation().getPassengerFirstName() + 
        		"! You have been invited to travel with your friends from " + departure + " to " + destination +
        		" on " + departureDate + ". Your seat is " + reservedSeat.getRed() + (char)(reservedSeat.getKolona() + 64) +
        		". Click on the following link to accept the invitation: http://localhost:8081/api/acceptInvitation/" + invitationCode);
    	
        try {
        	mailSender.send(mailMessage);
        } catch (MailException e) {
        	e.printStackTrace();
        }
    }
}