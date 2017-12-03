package Tools;

public class ServerMessage {
	String protocol;
	String message;

	public ServerMessage(String protocol, String message) {
		this.protocol = protocol;
		this.message = message;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getMessage() {
		return message;
	}
}
