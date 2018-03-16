package projet;

import java.util.ArrayList;
import java.util.HashMap;

public class Run {
	public int idRun;
	public String description;
	private int nbClient;
	private int nbToken;
	public HashMap<String,User> usersList;
	public ArrayList<String> choicesList;
	public int nextUserId;
	
	public Run(int id) {
		idRun = id;
		nbClient = 0;
		nbToken = 0;
		usersList = new HashMap<String,User>();
		choicesList = new ArrayList<String>();
		nextUserId = 0;
	}
	
	//Ajoute le client reçu en paramètre à la liste des clients du run
	public void addUser(User user){
		this.usersList.put(user.mailAdress, user);
		this.nextUserId++; //Incrémentation de cette variable pour un éventuel prochain ajout
		if (user instanceof Client) {
			nbClient++;
		}
	}
	
	public void setNbToken(int nb) {
		this.nbToken = nb;
	}
	
	public int getNbToken() {
		return this.nbToken;
	}
	
	public String toString() {
		return "Run : " + idRun + "; Users : " + usersList.values();
	}
}

