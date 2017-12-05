package Client;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import SuperServer.ChatRoom;
import SuperServer.ClientInfo;
import SuperServer.SuperServer;
import Tools.ClientMessage;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	private ChatRoomGUI chatRoomView;
	private String name;
	private transient ObjectOutputStream writer;
	private transient ObjectInputStream inputFromServer;
	private Socket socketServer;
	public static String host;
	ArrayList<ChatRoom> chatRoomList;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		host = "localhost";
		chatRoomView = new ChatRoomGUI(this);
		connectToSuperServer();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public void connectToSuperServer() {
		try {
			socketServer = new Socket(host, SuperServer.PORT_NUMBER);
			writer = new ObjectOutputStream(socketServer.getOutputStream());
			inputFromServer = new ObjectInputStream(socketServer.getInputStream());
			System.out.println("Found server who accepted me");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not find server at " + host + " on port " + SuperServer.PORT_NUMBER);
			System.exit(0);
		}

		try {
			chatRoomList = (ArrayList<ChatRoom>) inputFromServer.readObject();
			for (ChatRoom room : chatRoomList) {
				chatRoomView.model
						.addElement(room.getName() + " : " + room.getActiveUsers().size() + " active users\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Client lost server");
		}
		Thread t = new Thread(new ClientListener(this, inputFromServer));
		t.start();
	}

	public void sendMessageToSuperServer(ClientMessage clientMessage) throws IOException {
		writer.writeObject(clientMessage);
	}

	public void selectedChatRoomIndex(int index) throws IOException {
		ChatRoom room = null;
		System.out.println("index = " + index);
		if (index == -1 || index == 0) {
			// Create a new chatroom

			String newName;
			newName = JOptionPane.showInputDialog("Enter new chat room name");
			if (newName != null)
				sendMessageToSuperServer(
						new ClientMessage(new ClientInfo(name, host, SuperServer.PORT_NUMBER), "CREATE", newName));
		} else {
			room = chatRoomList.get(index - 1);
			sendMessageToSuperServer(
					new ClientMessage(new ClientInfo(name, host, SuperServer.PORT_NUMBER), "JOIN", room.getName()));

			// TODO: check if can connect

			// if connection is allowed
			// close chat list GUI and open chat client GUI
			Thread t = new Thread(new ChatClientGUI(this, room));
			t.start();
		}
		// System.out.println("Chosen chatroom's name is " + room.getName());

	}

	public void updateGUI(ArrayList<ChatRoom> roomList) {
		// System.out.println("Updating gui with new roomList whose size is " +
		// roomList.size());
		// System.out.println("Updating gui with new roomList whose room size is
		// " + roomList.get(0).getActiveUsers().size());
		this.chatRoomList = roomList;
		chatRoomView.updateRoomList();
	}

}