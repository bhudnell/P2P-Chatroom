package Client;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import SuperServer.ChatRoom;
import SuperServer.ClientInfo;
import SuperServer.SuperServerListener;
import Tools.ClientMessage;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	private ChatClientGUI chatView;
	private ChatRoomGUI chatRoomView;
	private String name;
	private boolean firstEntry = true;
	private transient ObjectOutputStream writer;
	private transient ObjectInputStream inputFromServer;
	private Socket socketServer;
	public static String host = "localhost";
	ArrayList<ChatRoom> chatRoomList;
	
	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		chatRoomView = new ChatRoomGUI(this);
		connectToSuperServer();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main() {
		new Client();
	}

	@SuppressWarnings("unchecked")
	public void connectToSuperServer() {
		try {
			socketServer = new Socket(host, ChatServer.PORT_NUMBER);
			writer = new ObjectOutputStream(socketServer.getOutputStream());
			inputFromServer = new ObjectInputStream(socketServer.getInputStream());
			System.out.println("Found server who accepted me");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not find server at " + host + " on port " + ChatServer.PORT_NUMBER);
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

	/*public void InputActionPerformed(ActionEvent ev) {
		try {
			if (firstEntry) {
				name = chatView.outgoing.getText();
				firstEntry = false;
				writer.writeObject(name + " has joined the chat");
			} else
				writer.writeObject(name + ": " + chatView.outgoing.getText());
			writer.flush();
			writer.reset();
		} catch (Exception ex) {
		}
		chatView.outgoing.setText("");
		chatView.requestFocus();
	}*/

	public void selectedChatRoomIndex(int index) throws IOException {
		ChatRoom room = null;
		System.out.println("index = " + index);
		if (index == -1 || index == 0) {
			// Create a new chatroom
			// JOptionPane newRoomName = new JOptionPane("Enter new chat room
			// name");
			String newName;
			newName = JOptionPane.showInputDialog("Enter new chat room name");
			if (newName != null)
				sendMessageToSuperServer(new ClientMessage(new ClientInfo(name, host, ChatServer.PORT_NUMBER), "CREATE", newName));
		} else {
			room = chatRoomList.get(index - 1);
			sendMessageToSuperServer(new ClientMessage(new ClientInfo(name, host, ChatServer.PORT_NUMBER), "JOIN", room.getName()));
			
			// connect to everyone in room
			
			
			
			// close chat list GUI and open chat client GUI
			chatRoomView.close();
			//chatView = new ChatClientGUI(this);
		}
		// System.out.println("Chosen chatroom's name is " + room.getName());

	}

	public void updateGUI(ArrayList<ChatRoom> roomList) {
		System.out.println("Updating gui with new roomList whose size is " + roomList.size());
		this.chatRoomList = roomList;
		chatRoomView.updateRoomList();
	}

}