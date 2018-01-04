package com.pp.cookforyou.utils;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

public class CFUUtils {
	
	public static void sendEmail(String email, String token)
	{
		String recipient = email;
		String sender = "admin@cookforyou.com";
		String url = "http://localhost:8080.com/users/confirmRegistration?email=" + email + "&token=" + token;
	 
	    // using host as localhost
		String host = "127.0.0.1";
	 
	    // Getting system properties
	    Properties properties = System.getProperties();
	 
	    // Setting up mail server
	    properties.setProperty("mail.smtp.host", host);
	 
	    // creating session object to get properties
	    Session session = Session.getDefaultInstance(properties);
	 
	    try
	    {
	    	// MimeMessage object.
	        MimeMessage message = new MimeMessage(session);
	 
	        // Set From Field: adding senders email to from field.
	        message.setFrom(new InternetAddress(sender));
	 
	        // Set To Field: adding recipient's email to from field.
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	 
	        // Set Subject: subject of the email
	        message.setSubject("Registration confirmation");
	 
	        StringBuilder content = new StringBuilder();
	        content.append("Please click on the link to confirm: <br/>");
	        content.append("<a href='" + url + "'>" + url + "</a>");
	        // set body of the email.
	        message.setContent(content, "text/html");
	 
	        // Send email.
	        Transport.send(message);
	    }
	    catch (MessagingException mex) 
	    {
	    	mex.printStackTrace();
	    }
	}
	
	public static String generateToken()
	{
		Random r = new Random();
		String characters = "0123456789abcdefghijklmnopqrsrtuwxyzABCDEFGHIJKLMNOPQRSTUVWXY";
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 20; i++)
		{
			s.append(characters.charAt(r.nextInt(characters.length())));
		}
		return s.toString();
	}
}
