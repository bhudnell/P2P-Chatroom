import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SuperServer {

	public static int PORT_NUMBER = 4004;

	public static void main(String[] args) {
		new SuperServer();
	}

	private ArrayList<ObjectOutputStream> clientOutputStreams;
	private ArrayList<ChatRoom> roomList;

	public SuperServer() {
		clientOutputStreams = new ArrayList<ObjectOutputStream>();
		roomList = new ArrayList<ChatRoom>();
		roomList.add(new ChatRoom("Public Chat 1"));
		roomList.add(new ChatRoom("Public Chat 2"));
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
			while (true) {
				Socket clientSocket = serverSock.accept();
				ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				writer.writeObject(roomList);
				// Send user the roomList object so they can display the chatrooms
				Thread t = new Thread(new LoopToReadClientsInputToServerInNewThread(clientSocket, clientOutputStreams));
				t.start();
				System.out.println("got a connection");
			}
		} catch (Exception ex) {
		}
	}
}
