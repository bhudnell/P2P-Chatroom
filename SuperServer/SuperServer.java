package SuperServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Client.*;
import Tools.ServerMessage;

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

	public void requestJoin(ClientInfo client, String roomName) {
		System.out.println("attempting to join room: " + roomName);
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
			System.out.println("added to room: " + roomName);
		} else {
			// need to ask user for a password, since there is one in place
		}

		for (ObjectOutputStream oos : clientOutputStreams) {
			try {
				ArrayList<ChatRoom> list = (ArrayList<ChatRoom>) roomList.clone();
				for (ChatRoom curr : roomList) {
					int index = roomList.indexOf(curr);
					ChatRoom tempRoom = new ChatRoom(curr.getName());
					for (ClientInfo info : curr.getActiveUsers()) {
						tempRoom.addClient(new ClientInfo(info.getName(), info.getIP(), info.getPort()));
					}
					list.remove(index);
					list.add(index, tempRoom);
				}

				oos.writeObject(new ServerMessage("UPDATE", "", list));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addClientToChatRoom(ClientInfo client, String roomName) {
		// need to setup adding a Client via ipAddress

		ChatRoom room = getChatRoom(roomName);
		if (room == null)
			return;
		room.addClient(client);

		// after adding a user to a chatroom, we can leave list of
		// chatrooms open and have that update consistently

	}

	public void createChatRoom(String message) {
		roomList.add(new ChatRoom(message));
		System.out.println("Roomlist size = " + roomList.size());
		for (ObjectOutputStream oos : clientOutputStreams) {
			try {
				oos.writeObject(new ServerMessage("UPDATE", "", (ArrayList<ChatRoom>) roomList.clone()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ChatRoom getChatRoom(String roomName) {
		for (ChatRoom room : roomList) {
			if (room.getName().equals(roomName))
				return room;
		}
		return null;
	}
}
