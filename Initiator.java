package user;

import exception.PermissionDeniedException;

public class Initiator extends User {
	
	public Initiator(String firstName, String lastName, String mailAdress) {
		super(firstName, lastName, mailAdress);
	}

	public String toString() {
		return "I :" + firstName + " " + lastName;
	}

	public void sendInvitation() throws PermissionDeniedException{
	}

	
	public void vote(int idChoice, int nbToken, int maxToken)throws PermissionDeniedException{
		throw new PermissionDeniedException();
	}
	
	public void addClient() throws PermissionDeniedException {
		
	}
	
	public void addChoice() throws PermissionDeniedException {
		throw new PermissionDeniedException();
	}
	
	public void addInitiator() throws PermissionDeniedException {
		
	}
	public void follow(User c) throws PermissionDeniedException {
		throw new PermissionDeniedException();
	}
	public void sendDecision() throws PermissionDeniedException {
		
	}
	
	public void description() throws PermissionDeniedException {
		
	}
	public void tokenCount() throws PermissionDeniedException {
		
	}
}
