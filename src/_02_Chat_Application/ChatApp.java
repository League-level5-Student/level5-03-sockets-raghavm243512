package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JOptionPane;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	public static void main(String[] args) {
		new ChatApp();
	}
	public ChatApp() {
		
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "",
				JOptionPane.YES_NO_OPTION);
		
		if(response==JOptionPane.YES_OPTION) {
			ServerSocket server = null;
			try {
				server = new ServerSocket(8080);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean running = true;
			// 4. Make a while loop that continues looping as long as the boolean created in
			// the previous step is true.
			while (running) {
				// 5. Make a try-catch block that checks for two types Exceptions:
				// SocketTimeoutException and IOException.
				// Put steps 8 - 15 in the try block.
				try {
					// 8. Let the user know that the server is waiting for a client to connect.
					//JOptionPane.showMessageDialog(null, "Connecting");
					// 9. Create an object of the Socket class and initialize it to
					// serverSocket.accept();
					// Change serverSocket to match the ServerSocket member variable you created in
					// step 1.
					// The program will wait her until either a client connects or the timeout
					// expires.
					System.out.println("Accepting");
					Socket s = server.accept();
					// 10. Let the user know that the client has connected.
					//JOptionPane.showMessageDialog(null, "Connected");
					// 11. Create a DataInputStream object. When initializing it, use the Socket
					// object you created in step 9 to call the getInputStream() method.
					System.out.println("making input stream");
					DataInputStream in = new DataInputStream(s.getInputStream());
					// 12. Print the message from the DataInputStream object using the readUTF()
					// method
					System.out.println(in.readUTF());
					// 13. Create a DataOutputStream object. When initializing it, use the Server
					// object you created in step 9 to call the getOutputStream() method.
					System.out.println("making output stream");
					DataOutputStream out = new DataOutputStream(s.getOutputStream());
					// 14. Use the DataOutputStream object to send a message to the client using the
					// writeUTF(String message) method.
					out.writeUTF(JOptionPane.showInputDialog("Send a message from server"));
					// 15. Close the client server
					s.close();
				} catch (SocketTimeoutException e) {
					// 6. If the program catches a SockeTimeoutException, let the user know about it
					// and set loop's boolean variable to false.
					running = false;
					e.printStackTrace();
				} catch (IOException f) {
					// 7. If the program catches a IOException, let the user know about it and set
					// the loop's boolean variable to false.
					running = false;
					f.printStackTrace();
				}
			}
			
		} else if (response==JOptionPane.NO_OPTION) {
			String serverip = getIPAddress();
			System.out.println(serverip);
			// 2. Create an integer for the server's port number
			int port = 8080;
			// 3. Surround steps 4-9 in a try-catch block that catches any IOExceptions.
			try {
				boolean running = true;
				while (running) {
				// 4. Create an object of the Socket class. When initializing the object, pass
				// in the ip address and the port number
				System.out.println("creating socket");
				Socket connection = new Socket(serverip, port);
				// 5. Create a DataOutputStream object. When initializing it, use the Socket
				// object you created in step 4 to call the getOutputStream() method.
				System.out.println("creating output");
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				// 6. Use the DataOutputStream object to send a message to the server using the
				// writeUTF(String message) method
				out.writeUTF(JOptionPane.showInputDialog("Send a message from client"));
				// 7. Create a DataInputStream object. When initializing it, use the Server
				// object you created in step 4 to call the getInputStream() method.
				System.out.println("creating input");
				DataInputStream in = new DataInputStream(connection.getInputStream());
				// 8. Use the DataInputStream object to print a message from the server using
				// the readUTF() method.
				System.out.println(in.readUTF());
				// 9. Close the client's server object
				connection.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}
	public static String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}
}
