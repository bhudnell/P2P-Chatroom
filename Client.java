
public class Client {

	private ChatClientGUI chatView;
	private ChatServer server;
	
	public Client() {
		chatView = new ChatClientGUI();
		server = new ChatServer();
	}
	
	public String getUsername() {
		return chatView.getUsername();
	}
}