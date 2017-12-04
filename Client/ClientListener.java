package Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import SuperServer.SuperServer;
import Tools.ClientMessage;
import Tools.ServerMessage;

public class ClientListener implements Runnable {

	private Client client;
	private ServerSocket serverSocket;
	ObjectInputStream ois;

	public ClientListener(Client client, ObjectInputStream ois) {
		this.client = client;
		this.ois = ois;
	}

	@Override
	public void run() {
		ServerMessage message;
		try {

			while (true) {
				// Wait for the server to send message
				message = (ServerMessage) ois.readObject();
				System.out.println("Received message from server");
				if (message.getProtocol().equals("UPDATE")) {
					client.updateGUI(message.getRoomList());
					System.out.println("Updating GUI aljkfghkljdf");
				}
			}
		} catch (Exception ex) {

		}
	}
}