package Tools;

import java.io.Serializable;
import java.util.ArrayList;

import SuperServer.ChatRoom;

public class ServerMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	String protocol;
	String message;
	ArrayList<ChatRoom> roomList;

	public ServerMessage(String protocol, String message, ArrayList<ChatRoom> roomList) {
		this.protocol = protocol;
		this.message = message;
		this.roomList = roomList;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getMessage() {
		return message;
	}
	public ArrayList<ChatRoom> getRoomList() {
		return roomList;
	}

}