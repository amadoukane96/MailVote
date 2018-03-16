package user;

import exception.PermissionDeniedException;

public abstract class User {
	public String idUser;
	public String idRun;

	protected String firstName;
	protected String lastName;
	public String mailAdress;
	
	public User(String firstName, String lastName, String mailAdress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailAdress = mailAdress;
		
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof User){
			User u = (User) o;
			return u.mailAdress == this.mailAdress;
		}	
		else{
			return false;
		}
	}
	public abstract void sendInvitation() throws PermissionDeniedException;
	public abstract void vote(int idChoice, int nbToken, int maxToken) throws PermissionDeniedException;
	public abstract void addClient() throws PermissionDeniedException;
	public abstract void addChoice() throws PermissionDeniedException;
	public abstract void addInitiator() throws PermissionDeniedException;
	public abstract void follow(User user) throws PermissionDeniedException;
	public abstract void sendDecision() throws PermissionDeniedException;
	public abstract void description() throws PermissionDeniedException;
	public abstract void tokenCount() throws PermissionDeniedException;
	
}