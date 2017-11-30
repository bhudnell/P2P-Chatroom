import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Client extends JFrame {

	private static final String ADDRESS = "localhost";

	public static void main(String[] args) throws UnknownHostException, IOException {
		String username = JOptionPane.showInputDialog("Username?");
		new Client(username).setVisible(true);
	}

	DefaultListModel<String> model;
	JTextField field;
	Socket socket;
	String username;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public static final int SERVER_PORT = 9091;
	

	public Client(String username) throws UnknownHostException, IOException {
		this.username = username;
		setupModelAndLayout();

		openConnection();

		field.addActionListener(new FieldListener());
		
		// TODO 7: Start a new ServerListener thread
		ServerListener serverListener = new ServerListener();
		serverListener.start();
	}

	private void setupModelAndLayout() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(550, 600);
		setLayout(new FlowLayout());

		model = new DefaultListModel<String>();

		JList<String> messages = new JList<>(model);
		JScrollPane scroll = new JScrollPane(messages);
		scroll.setPreferredSize(new Dimension(500, 500));
		add(scroll);

		field = new JTextField();
		field.setToolTipText("Send a message here!");
		field.setPreferredSize(new Dimension(500, 30));
		add(field);
	}

	private void openConnection() {
		/* Our server is on our computer, but make sure to use the same port. */
		try {
			// TODO 6: Connect to the Server
			
			Socket socket = new Socket("127.0.0.1", SERVER_PORT);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			model.addElement("Connected to server at " + ADDRESS + ":" + Server.SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class FieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 8: When the enter button is pressed, send the contents of the
			// JTextField to the server (add the username for extra style!)
		};
	}

	private class ServerListener extends Thread {

		@Override
		public void run() {
			// TODO 9: Repeatedly accept String objects from the server and add
			// them to our model.
		}
	}
}
