package Client;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClientGUI extends JFrame {

	private String name;
	private boolean firstEntry = true;
	public JTextField outgoing;
	private Client client;
	public JTextArea inputFromServerTextArea;

	public ChatClientGUI(Client client) {
		this.client = client;
		setTitle("Chat Client");
		setSize(380, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(null);

		outgoing = new JTextField("Replace me with your name");
		outgoing.addActionListener(new InputFieldListener());
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
	}

	public class InputFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			client.InputActionPerformed(ev);
			
		}
	}
}