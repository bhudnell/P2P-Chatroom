import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

	public static final int SERVER_PORT = 9001;

	private static ServerSocket sock;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());

	public static void main(String[] args) throws IOException {
		sock = new ServerSocket(SERVER_PORT);
		System.out.println("Server started on port " + SERVER_PORT);

		while (true) {
			// TODO 1: Accept a connection from the ServerSocket.
			Socket s = null;

			ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

			// TODO 2: Save the output stream to our clients list so we can
			// broadcast to this client later

			// TODO 3: Start a new ClientHandler thread for this client.

			System.out.println("Accepted a new connection from " + s.getInetAddress());
		}
	}
}

class ClientHandler extends Thread {

	ObjectInputStream input;
	List<ObjectOutputStream> clients;

	public ClientHandler(ObjectInputStream input, List<ObjectOutputStream> clients) {
		this.input = input;
		this.clients = clients;
	}

	@Override
	public void run() {
		while (true) {

			String s = null;

			// TODO 4: Read a String from the client

			writeStringToClients(s);

		}

	}

	private void writeStringToClients(String s) {
		// TODO 5: Send a string to all clients in the client list
	}

	// TODO (When you get the chance): Write a method that closes all the
	// resources of a ClientHandler and logs a message, and call it from every
	// place that a fatal error occurs in ClientHandler (the catch blocks that
	// you can't recover from).
}
