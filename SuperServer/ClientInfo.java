package SuperServer;

import java.io.Serializable;

public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name, IP;
	private int port;
	
	public ClientInfo(String name, String IP, int port) {
		this.name = name;
		this.IP = IP;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIP() {
		return IP;
	}
	
	public int getPort() {
		return port;
	}
}
