package SuperServer;
import java.io.Serializable;
import java.util.ArrayList;

import Client.Client;

public class ChatRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String password;
	ArrayList<Client> activeUsers;
	public String getName() {
		return name;
	}

	public ArrayList<Client> getActiveUsers() {
		return activeUsers;
	}
	
	public ChatRoom(String name){
		this.name = name;
		activeUsers = new ArrayList<Client>();
		password = "";
	}
	
	public void addClient(Client client){
		activeUsers.add(client);
	}
	
	public void removeClient(Client client){
		activeUsers.remove(client);
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public boolean gainAccess(String password){
		return this.password.equals(password);
	}
	
}
