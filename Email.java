package projet;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public class Email {
	Message message;

	private PrintStream originalOut;
    private ByteArrayOutputStream collectedOut;
	
	public Email(Message m) {
		message = m;
	}
	
	private String getSender() {
		Address[] froms = new Address[1];
		try {
			collectSystemOut();
			froms = message.getFrom();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		restoreOriginalSystemOut();
		return ((InternetAddress) froms[0]).getAddress();
	}
          
	private void seen() {
		
	}
	
	private String getSubject() {
		String subject;
		try {
			subject = message.getSubject();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			subject = "";
		}
		return subject;
	}
	
	public ArrayList<String> getContent() {
		String str = "";
		
		try {
			str = getTextFromMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] commandTab = str.split(System.getProperty("line.separator"));
		ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(commandTab));
		
		return commandList;
	}
	
	private String getTextFromMessage() throws Exception {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}
	
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception{
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + html + "\n" ;
			} else if (bodyPart.getContent() instanceof MimeMultipart){
				result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
			}
		}
		return result;
	}
	
	public void collectSystemOut() {
        originalOut = System.out;
        collectedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(collectedOut));
    }

    public void restoreOriginalSystemOut() {
        System.setOut(originalOut); //Pour de nouveau pouvoir afficher nos print sur la sortie standard initiale
    }
}
