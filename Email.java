
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class Email
{
	public Boolean sendIp(Parameters param) {
		Boolean response = false;
		
		try {
			Message message = new MimeMessage(getSession(param));
			message.addRecipient(RecipientType.TO, new InternetAddress(param.email));
			message.addFrom(new InternetAddress[] { new InternetAddress(param.from) });
			
			message.setSubject("Seu IP mudou");
			message.setContent("Anote seu novo IP " + param.ip, "text/plain");
			
			Transport.send(message);
			response = true;
		} catch (MessagingException mex) {
	         mex.printStackTrace();
	    }
		
		return response;
	}
	
	private Session getSession(Parameters param) {
		Authenticator authenticator = new Authenticator(param);
		
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", param.smtp);
		properties.setProperty("mail.smtp.port", param.port);
		
		return Session.getInstance(properties, authenticator);
	}
}

class Authenticator extends javax.mail.Authenticator {
	
	private PasswordAuthentication authentication;
	
	public Authenticator(Parameters param) {
		String username = param.emailSmtp;
		String password = param.passwdSmtp;
		authentication = new PasswordAuthentication(username, password);
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}