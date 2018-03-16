package projet;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MailVote {
	
	public HashMap<Integer, Run> runs;
	private int nextRunId;
	private int activeRunId;
	private HashMap<String, String> methodMap; //Associe le nom de la commande au nom de la méthode
	
	/*
	public void start() {
		for(Email m: Email.read_inbox()){
			for(String s: parse(m)){
				executeCommand(s);
			}
		}
	}
	*/
	public MailVote() {
		runs = new HashMap<Integer, Run>();
		nextRunId = 0;
		methodMap = new HashMap<String, String>();
		//Ajout des correspondances entre nom de commande et nom de méthode
		methodMap.put("ADDCLIENT","addClient");
		methodMap.put("CREATERUN", "createRun");
		methodMap.put("RUN", "selectRun");
	}
	
	//Prend en paramètre une ligne de commande sous forme de chaine de caractère et appelle la fonction correspondante
	public int executeCommand(String commandLine) {
		ArrayList<String> command = new ArrayList<String>(Arrays.asList(commandLine.split(" ")));
		String methodName;
		if (!commandLine.equals("\n") ){
			methodName = command.get(0).replaceAll("(\\r|\\n)", "");
		try {
			if (!methodMap.containsKey(methodName)) { //Si la commande passée en paramètre n'existe pas
				System.out.println("Mauvaise commande" + command.get(0));
				return 1;
			}
			else {
				methodName = methodMap.get(methodName);
				Method method = this.getClass().getMethod(methodName, List.class);
				System.out.println("méthode appelée : " + methodName);
			  
				try { //On appelle la méthode avec les paramètres reçus
					method.invoke(this, command.subList(1, command.size()));
				
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					return 3;
				} catch (InvocationTargetException e) {
					if (e.getCause().toString().equals("projet.NoSuchRunException")) { //Si la commande passée en paramètre n'existe pas
					  return 2;
					}
					return 4;
				
				} catch (NullPointerException e) { //Si la commande n'a pas de paramètre
					try {
						method.invoke(this, command); 
					} catch (IllegalAccessException ex) {
						e.printStackTrace();
					} catch (IllegalArgumentException ex) {
						return 3;
					} catch (InvocationTargetException ex) {
						return 4;
					}
				}
			}
		}
		catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) { //Si la commande passée en paramètre n'existe pas
			  return 1;
		}
		return 0;
		} 
		else {
			return 5;
		}
	}
	
	//Crée un objet client avec les paramètes si ceux ci sont correctes et l'ajoute au run actif
	public void addClient(List<String> parameters) {
		
		if (parameters.size() == 3) { //Cas où les paramètres sont correctes
			//Création du nouvel objet Client avec les paramètres de la commande et en lui assignant le bon id
			//Puis ajout de ce Client à la liste des users du run courant
			Client newClient = new Client(this.runs.get(activeRunId).nextUserId, parameters.get(0), parameters.get(1), parameters.get(2), this.runs.get(this.activeRunId).getNbToken());
			this.runs.get(this.activeRunId).addUser(newClient);
		}
		else { //Le cas où les paramètres ne sont pas correctes
			throw new IllegalArgumentException(); 
		}
	}
	
	//Crée un run et l'ajoute à la liste des run
	public void createRun(List<String> parameters) {
		
		if (parameters.size() == 0) { //Cas où les paramètres sont correctes
			this.runs.put(this.nextRunId, new Run(this.nextRunId)); //Ajout du nouvel objet Run à la hashMap
			this.activeRunId = this.nextRunId;
			this.nextRunId++; //Incrémentation de cette variable pour un éventuel prochain ajout
		}
		else { //Le cas où les paramètres ne sont pas correctes
			throw new IllegalArgumentException(); 
		}
	}
	
	//Sélectionne comme run actif le run dont le numero est donné en paramètre 
	public void selectRun(List<String> parameters) throws NoSuchRunException {
		if (parameters.size() == 1) { //Cas où le nombre de paramètre est correcte
			try {
				int id = Integer.parseInt(parameters.get(0)); //Récupération de l'entier correspondant à cette chaîne de caractère
				if (this.runs.containsKey(id)) {  //S'il y a bien un run qui possède cet id
					this.activeRunId = Integer.parseInt(parameters.get(0));
				}
				else { //Si aucun run ne possède cet entier
					throw new NoSuchRunException();
				}
			}
			catch (NumberFormatException e) { //Dans le cas où le paramètre n'est pas un entier
				throw new IllegalArgumentException(); 
			}
		}
		else { //Cas où le nombre de paramètre est incorrect
			throw new IllegalArgumentException(); 
		}
	}
}