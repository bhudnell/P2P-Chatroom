import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Client {

	private ChatClientGUI chatView;
	private ChatServer server;
	private String username;
	private String IP;
	private String name;
	private boolean firstEntry = true;
	private JTextField outgoing;
	private ObjectOutputStream writer;
	private ObjectInputStream inputFromServer;
	private Socket socketServer;
	public static String host = "localhost";

	public Client() {
		chatView = new ChatClientGUI(this);
		server = new ChatServer();
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

	public void connectToSuperServer() {
		try {
			// host could be "localhost", port could be 4000
			socketServer = new Socket(host, ChatServer.PORT_NUMBER);
			writer = new ObjectOutputStream(socketServer.getOutputStream());
			inputFromServer = new ObjectInputStream(socketServer.getInputStream());
			System.out.println("Found server who accepted me");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not find server at " + host + " on port " + ChatServer.PORT_NUMBER);
			System.exit(0);
		}

		String message;
		try {
			while (true) {
				message = (String) inputFromServer.readObject();
				chatView.inputFromServerTextArea.append(message + "\n");
			}
		} catch (Exception ex) {
			System.out.println("Client lost server");
		}
	}

	public class InputFieldListener implements ActionListener {
		// Precondition: This client has successfully connected to
		// a server and writer is a reference to the server's output stream.
		public void actionPerformed(ActionEvent ev) {

			try {
				if (firstEntry) {
					name = outgoing.getText();
					firstEntry = false;
					writer.writeObject(name + " has joined the chat");
				} else
					writer.writeObject(name + ": " + outgoing.getText());
				writer.flush();
			} catch (Exception ex) {
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}

	public void InputActionPerformed(ActionEvent ev) {
		try {
			if (firstEntry) {
				name = outgoing.getText();
				firstEntry = false;
				writer.writeObject(name + " has joined the chat");
			} else
				writer.writeObject(name + ": " + outgoing.getText());
			writer.flush();
		} catch (Exception ex) {
		}
		outgoing.setText("");
		outgoing.requestFocus();
		
	}
}