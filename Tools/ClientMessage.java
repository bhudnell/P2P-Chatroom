package Tools;
import java.io.Serializable;

import Client.*;
import SuperServer.ClientInfo;

public class ClientMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	ClientInfo client;
	String protocol;
	String message;

	public ClientMessage(ClientInfo client, String protocol, String message) {
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

	public ClientInfo getClient() {
		return client;
	}
}
