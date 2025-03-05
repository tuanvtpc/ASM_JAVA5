package com.fpoly.java5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	 @Autowired
	    private JavaMailSender mailSender;

	    public void sendPasswordResetEmail(String toEmail, String token) {
	        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("your-email@gmail.com");
	        message.setTo(toEmail);
	        message.setSubject("Yêu cầu đặt lại mật khẩu");
	        message.setText("Mã xác nhận của bạn: " + token + 
	                        "\nNhấp vào liên kết sau để đặt lại mật khẩu: " + resetUrl);

	        mailSender.send(message);
	    }
}
