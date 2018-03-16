package projet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailServer {
		final String username;
		final String password;
		private Properties props;
		private Session session;
		private PrintStream originalOut;
	    private ByteArrayOutputStream collectedOut;
	
		public MailServer(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		public ArrayList<Email> getMails() throws MessagingException, IOException {
	    	ArrayList<Email> mails  = new ArrayList<Email>();
	    	collectSystemOut();
	    	props = System.getProperties();
			props.setProperty("mail.store.protocol", "imap");
			session = Session.getDefaultInstance(props, null);
			session.setDebug(true);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", "polymailvote", "polytech3a");
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);
			Message messages[] = inbox.getMessages();
			for(Message message:messages) {	
				mails.add(new Email(message));
			}
			restoreOriginalSystemOut();
			return mails;
		}
		
		
		/*
		public void sendMail(String destinator, String subject, String body) throws RuntimeException {
			collectSystemOut(); //Pour ne pas afficher sur notre sortie les prints que font les fonctions suivantes
			props = new Properties();
	  		props.put("mail.smtp.auth", "true");
	  		props.put("mail.smtp.starttls.enable", "true");
	  		props.put("mail.smtp.host", "smtp.gmail.com");
	  		props.put("mail.smtp.port", "587");
	  		session = Session.getInstance(props,
	  		new javax.mail.Authenticator() {
	  			protected PasswordAuthentication getPasswordAuthentication() {
	  				return new PasswordAuthentication(username, password);
	  			}
	  		});

	  		try {
	  			Message message = new MimeMessage(session);
	  			message.setFrom(new InternetAddress("polymailvote@gmail.com"));
	  			message.setRecipients(Message.RecipientType.TO,
	  			InternetAddress.parse(destinator));
	  			message.setSubject(subject);
	  			message.setText(body);
	  			Transport.send(message);
	  			restoreOriginalSystemOut();
	  			System.out.println("Mail sent");
	  		} catch (MessagingException e) {
	  			throw new RuntimeException(e);
	  		}
		}
		
	    */
		public void collectSystemOut() {
	        originalOut = System.out;
	        collectedOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(collectedOut));
	    }

	    public void restoreOriginalSystemOut() {
	        System.setOut(originalOut); //Pour de nouveau pouvoir afficher nos print sur la sortie standard initiale
	    }
}
	      
	  
