import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatRoomGUI extends JFrame {

	private String name;
	private boolean firstEntry = true;
	public JTextField outgoing;
	private Client client;
	public JTextArea inputFromServerTextArea;
	public JList jList;
	public DefaultListModel<String> model;

	public ChatRoomGUI(Client client) {
		this.client = client;
		setTitle("Chat Room List");
		setSize(380, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(null);
		model = new DefaultListModel<String>();
		
		JLabel label = new JLabel("Double Click a chat room to join");
		label.setSize(300, 20);
		label.setLocation(30, 10);
		cp.add(label);
		
		jList = new JList<String>(model);
		jList.setSize(300, 20);
		
		jList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		        } else if (evt.getClickCount() == 3) {
		            // Triple-click detected
		            int index = list.locationToIndex(evt.getPoint());
		        }
		    }
		});
		
//		outgoing = new JTextField("Replace me with your name");
//		outgoing.addActionListener(new InputFieldListener());
//		outgoing.setSize(300, 20);
//		outgoing.setLocation(30, 10);
//		cp.add(outgoing);

		inputFromServerTextArea = new JTextArea();

		JScrollPane scroller = new JScrollPane(jList);
		scroller.setSize(300, 400);
		scroller.setLocation(30, 40);
		scroller.setBackground(Color.WHITE);
		cp.add(scroller);

		setVisible(true);
		//outgoing.requestFocus();
	}

	public class InputFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			client.InputActionPerformed(ev);
			
		}
	}
}