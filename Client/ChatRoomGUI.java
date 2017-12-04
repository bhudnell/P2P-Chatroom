package Client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SuperServer.ChatRoom;
import SuperServer.SuperServer;

public class ChatRoomGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public JTextField outgoing;
	private Client client;
	public JTextArea inputFromServerTextArea;
	public JList<String> jList;
	public DefaultListModel<String> model;
	int index;
	Container cp;

	public ChatRoomGUI(Client client) {
		this.client = client;
		setTitle("Chat Room List");
		setSize(380, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		cp.setLayout(null);
		model = new DefaultListModel<String>();

		JLabel label = new JLabel("Double Click a chat room to join");
		label.setSize(300, 20);
		label.setLocation(30, 10);
		cp.add(label);

		jList = new JList<String>(model);
		model.addElement("Add new chat room");
		jList.setSize(300, 20);
		index = -1;
		jList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					index = list.locationToIndex(evt.getPoint());
					try {
						client.selectedChatRoomIndex(index);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (evt.getClickCount() == 3) {
					// Triple-click detected
					index = list.locationToIndex(evt.getPoint());
				}

			}
		});

		inputFromServerTextArea = new JTextArea();

		JScrollPane scroller = new JScrollPane(jList);
		scroller.setSize(300, 400);
		scroller.setLocation(30, 40);
		scroller.setBackground(Color.WHITE);
		cp.add(scroller);

		setVisible(true);
		// outgoing.requestFocus();
	}

	/*public class InputFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			client.InputActionPerformed(ev);

		}
	}
*/
	public void updateRoomList() {
		cp.removeAll();
		cp.setLayout(null);
		model = new DefaultListModel<String>();
		JLabel label = new JLabel("Double Click a chat room to join");
		model.addElement("Add new chat room");
		for (ChatRoom room : client.chatRoomList) {
			model.addElement(room.getName() + " : " + room.getActiveUsers().size() + " active users\n");
		}

		label.setSize(300, 20);
		label.setLocation(30, 10);
		cp.add(label);

		jList = new JList<String>(model);

		jList.setSize(300, 20);
		index = -1;
		jList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					index = list.locationToIndex(evt.getPoint());
					try {
						client.selectedChatRoomIndex(index);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (evt.getClickCount() == 3) {
					// Triple-click detected
					index = list.locationToIndex(evt.getPoint());
				}

			}
		});

		inputFromServerTextArea = new JTextArea();

		JScrollPane scroller = new JScrollPane(jList);
		scroller.setSize(300, 400);
		scroller.setLocation(30, 40);
		scroller.setBackground(Color.WHITE);
		cp.add(scroller);

		setVisible(true);
		// outgoing.requestFocus();
	}

	public void close() {
		this.dispose();
	}
}