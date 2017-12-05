package Client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SuperServer.ChatRoom;
import SuperServer.ClientInfo;
import Tools.ClientMessage;

public class ChatClientGUI extends JFrame implements Runnable {

	private String name;
	private boolean firstEntry = true;
	public JTextField outgoing;
	private ChatRoom room;
	public JTextArea inputFromServerTextArea;
	private transient ObjectOutputStream writer;
	private transient ObjectInputStream inputFromServer;
	private Socket socketServer;
	public static String host = "localhost";
	private boolean isFirst;
	private Client client;

	public ChatClientGUI(Client client, ChatRoom room) {
		this.client = client;
		this.room = room;
		isFirst = room.getActiveUsers().isEmpty();
		setTitle("Room: " + room.getName());
		setSize(380, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(null);

		outgoing = new JTextField("Replace me with your name");
		outgoing.addActionListener(new InputFieldListener());
		this.addWindowListener(new onCloseListener(this));
		outgoing.setSize(300, 20);
		outgoing.setLocation(30, 10);
		cp.add(outgoing);

		inputFromServerTextArea = new JTextArea();

		JScrollPane scroller = new JScrollPane(inputFromServerTextArea);
		scroller.setSize(300, 400);
		scroller.setLocation(30, 40);
		scroller.setBackground(Color.WHITE);
		cp.add(scroller);
		setVisible(true);
		outgoing.requestFocus();
		connectToPeers();
	}

	private void connectToPeers() {
		if (isFirst) {
			Thread t = new Thread(new ChatServer());
			t.start();
		}
		try {
			// host could be "localhost", port could be 4000
			socketServer = new Socket(host, ChatServer.PORT_NUMBER);
			System.out.println("pre akdsjfhs");
			writer = new ObjectOutputStream(socketServer.getOutputStream());
			inputFromServer = new ObjectInputStream(socketServer.getInputStream());
			System.out.println("Found client who accepted me");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Could not find server at " + host + " on port " + ChatServer.PORT_NUMBER);
			System.exit(0);
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
				} else {
					writer.writeObject(name + ": " + outgoing.getText());
				}
				writer.flush();
			} catch (Exception ex) {
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	
	public class onCloseListener implements WindowListener {
		
		private JFrame frame;
		
		public onCloseListener(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			try {
				client.sendMessageToSuperServer(new ClientMessage(null, "EXIT", room.getName()));
				frame.dispose();
			} catch (IOException e) {
			}
		}

		@Override
		public void windowClosing(WindowEvent arg0) {			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public void run() {
		String message;
		try {
			while (true) {
				message = (String) inputFromServer.readObject();
				inputFromServerTextArea.append(message + "\n");
			}
		} catch (Exception ex) {
			System.out.println("Client lost server");
		}
	}
}