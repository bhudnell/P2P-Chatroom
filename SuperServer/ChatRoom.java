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
	ArrayList<ClientInfo> activeUsers;

	public ChatRoom(String name){
		this.name = name;
		activeUsers = new ArrayList<ClientInfo>();
		password = "";
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<ClientInfo> getActiveUsers() {
		return activeUsers;
	}
	
	public void addClient(ClientInfo client){
		activeUsers.add(client);
		System.out.println("Adding " + client.getName() + " and ip " + client.getIP() + " to " + getName());
	}
	
	public void removeClient(ClientInfo client){
		activeUsers.remove(client);
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public boolean gainAccess(String password){
		return this.password.equals(password);
	}

	public void addClient(String username, String ipAddress) {
		System.out.println("Adding " + username + " and ip " + ipAddress + " to " + getName());
		
	}
	
}
