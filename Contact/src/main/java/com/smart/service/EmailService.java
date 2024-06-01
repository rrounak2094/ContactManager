package com.smart.service;

import java.io.File;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	public boolean sendEmail(String subject, String message, String to)
	{
		boolean f=false;
		
		String from="ronakrathore058@gmail.com";
		
		//variable for gmail
		String host="smtp.gmail.com";
		
		//get the System propertites
		Properties pro=System.getProperties();
		System.out.println("pro"+pro);
		
		//setting important information to properties obj
		
		// host set
		pro.put("mail.smtp.host", host);
		pro.put("mail.smtp.port", "465");
		pro.put("mail.smtp.ssl.enable", "true");
		//pro.put("mail.smtp.starttls.enable", "true");
		
		//step1: to get the session obj
		Session session=Session.getInstance(pro, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("ronakrathore058@gmail.com","leuk zdsz jfoz bfzi");
			}
		});
		
		session.setDebug(true);
		
		//Step2: compose the message[text,multi,media]
		MimeMessage m=new MimeMessage(session);
		
		try {
			
			//from email
			m.setFrom(from);
			
			//aading receipient
			m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
			//adding subject to message
			m.setSubject(subject);
			
			//adding text to message
			m.setContent(message,"text/html");
			
			//Step3: send message using transport class
			Transport.send(m);
			
			System.out.println("success");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return f;
	}

}
