package org.jsp.dre.service;

import org.jsp.dre.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender jms;

	public void sendEmail(User u) {
		
		MimeMessage mm= jms.createMimeMessage()	;
		try {
			MimeMessageHelper mmh=new MimeMessageHelper(mm,true);
			mmh.setTo(u.getEmail());
			mmh.setSubject("DRE Account Created");
			mmh.setText("Dear "+u.getName()+" Your Dating Recommendation Application is Created Successfully Done");
			jms.send(mm);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}