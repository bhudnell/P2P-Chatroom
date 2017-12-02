
/**
 * An instance of this class acts as the liaison between each client
 * and the one server.  The infinite read loop waits for input from
 * one client.  Concurrently, the server is waiting for new connections
 * in its infinite loop. 
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LoopToReadClientsInputToServerInNewThread implements Runnable {

	ObjectInputStream reader;
	Socket sock;
	private ArrayList<ObjectOutputStream> clientOutputStreams;

	public LoopToReadClientsInputToServerInNewThread(Socket clientSocket,
			ArrayList<ObjectOutputStream> clientOutputStreams) {
		this.clientOutputStreams = clientOutputStreams;
		try {
			sock = clientSocket;
			if (sock == null)
				JOptionPane.showMessageDialog(null, "socket is null");
			reader = new ObjectInputStream(sock.getInputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO: Write a loop that waits for one client's input.
		// When received, send a message to all connected clients.
		String message;
		try {

			while (true) {
				// Wait for the client send a writeObject message to the server
				message = (String) reader.readObject();
				// Send the same message from the server to all clients
				tellEveryone(message);
			}
		} catch (Exception ex) {

		}
	}

	// Send the same message to all clients
	public void tellEveryone(String message) {
		for (ObjectOutputStream output : clientOutputStreams) {
			try {
				output.writeObject(message);
				output.flush();
			} catch (Exception ex) {
				clientOutputStreams.remove(output);
			}
		}
	}
}

/*
  

*/