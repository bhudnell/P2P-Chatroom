import java.util.ArrayList;

public class ChatRoom {
	String name;
	String password;
	public String getName() {
		return name;
	}

	public ArrayList<Client> getActiveUsers() {
		return activeUsers;
	}

	ArrayList<Client> activeUsers;
	public ChatRoom(String name){
		this.name = name;
		activeUsers = new ArrayList<Client>();
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
