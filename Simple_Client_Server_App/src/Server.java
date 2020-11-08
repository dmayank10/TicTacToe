import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * This is the server class that takes in a string from the client and checks
 * if it is a palidrome.
 * 
 * The purpose of this class is to gain familiarity with creating a serve to connect to 
 * a client.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 7, 2020
 *
 */
public class Server {
	/**
	 * The endpoint for the sever socket
	 */
	private Socket palinSocket;
	/**
	 * The socket that waits for a request from the network
	 */
	private ServerSocket serverSocket;
	/**
	 * The output text stream
	 */
	private PrintWriter socketOut;
	/**
	 * Reads a string of characters from an input
	 */
	private BufferedReader socketIn;
	/**
	 * The constructor initializes the serversocket object to the same same address as the client.
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(8099);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method tries to read a line from the client, calls the checkPalidrome method,
	 * and returns if the string is a palidrome. 
	 */
	public void communicate() {
		String line = "";
		while(true) {
			try {
				line = socketIn.readLine();
				line = checkPalidrome(line);
				socketOut.println(line);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This is the private method that takes a string, checks if it's a palidrome and 
	 * returns a string that says whether or not it is a palidrome
	 * @param s is the string to check
	 * @return a sting that informs the user if the string is a palidrome
	 */
	private String checkPalidrome(String s) {
		if(s==null)
			return "";
		String temp = s+" is a Palidrome";
		int end = s.length()-1;
		for(int begin = 0; begin<s.length()/2;begin++) {
			if(s.charAt(begin)!= s.charAt(end))
				temp = s+" is not a Palidrome";
			end--;
		}
		return temp;
	}
	/**
	 * This is the main method that takes in an input stream and sends an output stream.  These closes the sockets
	 * when the tasks are finished
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Server myServer = new Server();
		
		try {
			myServer.palinSocket = myServer.serverSocket.accept();
			System.out.println("Server is running...");
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.palinSocket.getInputStream()));
			myServer.socketOut = new PrintWriter(myServer.palinSocket.getOutputStream(), true);
			myServer.communicate();
			
			myServer.socketIn.close();
			myServer.socketOut.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
