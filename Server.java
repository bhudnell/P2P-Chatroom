import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class Server extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int SERVER_PORT = 9091;

	private static ServerSocket sock;
	private static Map<String, List<ClientStreams>> clientStreamsMap = new HashMap();
	// Maps chatroom name to list of clients within said chatroom

	public static void main(String[] args) throws IOException {

		sock = new ServerSocket(SERVER_PORT);

		System.out.println("Server started on port " + SERVER_PORT);

		while (true) {
			Socket s = null;
			System.out.println("Preaccept");
			s = sock.accept();
			System.out.println("Post accept");

			ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

			ClientHandler clientHandler = new ClientHandler(is);
			clientHandler.start();

			System.out.println("Accepted a new connection from " + s.getInetAddress());
			clientHandler.writeStringToClient(os, "New User Logged In\n");
		}
	}
}

class ClientStreams {
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public ClientStreams(ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}
}

class ClientHandler extends Thread {

	ObjectInputStream input;

	public ClientHandler(ObjectInputStream input) {
		this.input = input;
	}

	@Override
	public void run() {
		String currString;
		while (true) {
			try {
				currString = input.readUTF();
				System.out.println(currString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void writeStringToClient(ObjectOutputStream client, String s) {
		// TODO 5: Send a string to all clients in the client list
		try {
			client.writeObject(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO (When you get the chance): Write a method that closes all the
	// resources of a ClientHandler and logs a message, and call it from every
	// place that a fatal error occurs in ClientHandler (the catch blocks that
	// you can't recover from).
}
