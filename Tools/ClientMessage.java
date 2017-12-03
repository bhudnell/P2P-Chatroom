package Tools;
import java.io.Serializable;

import Client.*;

public class ClientMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	String username;
	String protocol;
	String message;

	public ClientMessage(String username, String protocol, String message) {
		this.username = username;
		this.protocol = protocol;
		this.message = message;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}
}
