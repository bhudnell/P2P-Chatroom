package SuperServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Client.*;
import Tools.*;
import SuperServer.*;

public class SuperServerListener implements Runnable {

	ObjectInputStream ois;
	Socket socket;
	SuperServer superServer;

	public SuperServerListener(SuperServer superServer, Socket socket) {
		this.superServer = superServer;
		try {
			this.socket = socket;
			if (socket == null)
				JOptionPane.showMessageDialog(null, "socket is null");
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		ClientMessage clientMessage;
		Client client;
		String protocol;
		String message;
		try {

			while (true) {
				// Wait for the client send a writeObject message to the server
				clientMessage = (ClientMessage) ois.readObject();
				client = clientMessage.getClient();
				protocol = clientMessage.getProtocol();
				message = clientMessage.getMessage();
				if(protocol.contains("$JOIN$")){
					// Client is requesting to join a chatroom
					superServer.requestJoin(client,message);
				}
				if(protocol.contains("$AUTH$")){
					// Client is providing password to join a chatroom
				}
				
			}
		} catch (Exception ex) {

		}
	}
}