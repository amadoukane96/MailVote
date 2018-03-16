package projet;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;

public class Main {
	public static void main(String[] args) {
		MailVote m = new MailVote();
		MailServer mailServer = new MailServer("polymailvote@gmail.com", "polytech3a");
		ArrayList<Email> mails = new ArrayList<Email>();
		
		//mailServer.sendMail("mmoussamokhtar@yahoo.fr", "coucou", "I am the spirit of polytech mail vote, caca de chameau");
		
		try {
			mails = mailServer.getMails();
			
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int commandOk = 0;
		for (Email mail: mails) {
			System.out.println("Nombre de ligne : " + mail.getContent().size());
			System.out.println("Contenu du mail : ");
			for (int i = 0; i < mail.getContent().size(); i++) {
				System.out.println(mail.getContent().get(i));
	      	}
		
			System.out.println("\nExécution des commandes : ");
			for (int i = 1; i < mail.getContent().size(); i++) {
				commandOk = m.executeCommand(mail.getContent().get(i));
				/*
				if (commandOk != 0){
					break;
				}	*/
			
				if (commandOk == 5) { //nom de commande inexistant
					System.out.println("Ligne vide");
				}
				if (commandOk == 1) { //nom de commande inexistant
					System.out.println(" - Erreur : La commande n'est pas connue");
				}
				if (commandOk == 2) { //Identifiant de run inexistant
					System.out.println(" - Erreur : Ce run n'existe pas");
				}
				if (commandOk == 3 || commandOk == 4) { //Mauvais arguments
					System.out.println(" - Erreur : Mauvais arguments");
				}
			}
		System.out.println("-----------------------------------------------------");
		}
		
		System.out.println("Voici les runs après exécution des commandes :\n" + m.runs.values());
	}
}