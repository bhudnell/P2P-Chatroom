package SuperServer;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Client.*;

public class SuperServer {

	public static int PORT_NUMBER = 4004;

	public static void main(String[] args) {
		new SuperServer();
	}

	private ArrayList<ObjectOutputStream> clientOutputStreams;
	private ArrayList<ChatRoom> roomList;

	public SuperServer() {
		clientOutputStreams = new ArrayList<ObjectOutputStream>();
		roomList = new ArrayList<ChatRoom>();
		roomList.add(new ChatRoom("Add new Chatroom"));
		roomList.add(new ChatRoom("Public Chat 2"));
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
			while (true) {
				Socket clientSocket = serverSock.accept();
				ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				writer.writeObject(roomList);
				// Send user the roomList object so they can display the
				// chatrooms
				Thread t = new Thread(new SuperServerListener(this, clientSocket));
				t.start();
				System.out.println("got a connection");
			}
		} catch (Exception ex) {
		}
	}

	public void requestJoin(Client client, String roomName) {
		ChatRoom room = null;
		for (ChatRoom currRoom : roomList) {
			if (currRoom.getName().equals(roomName)) {
				room = currRoom;
				break;
			}
		}
		// room is now set if found
		if (room.gainAccess("")) {
			// if there is no password, let user enter room
			addClientToChatRoom(client, roomName);
		} else {
			// need to ask user for a password, since there is one in place
		}
	}

	private void addClientToChatRoom(Client client, String roomName) {
		// need to setup adding a Client to a chatroom

		// after adding a user to a chatroom, we can either leave list of
		// chatrooms open and have that update consistently, or we can close
		// chatroom list once client joins a chatroom

	}
}
