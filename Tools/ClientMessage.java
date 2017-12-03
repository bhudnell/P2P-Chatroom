package Tools;
import Client.*;

public class ClientMessage {
	Client client;
	String protocol;
	String message;

	public ClientMessage(Client client, String protocol, String message) {
		this.client = client;
		this.protocol = protocol;
		this.message = message;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getMessage() {
		return message;
	}

	public Client getClient() {
		return client;
	}
}
