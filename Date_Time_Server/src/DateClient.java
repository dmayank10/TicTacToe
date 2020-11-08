import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * This is the Date Client class that connects to the Date Server.  Prompts the user to enter either 'DATE' or  'TIME' and asks
 * the server to send the corresponding information back.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 8, 2020
 *
 */
public class DateClient {
	/**
	 * The variable to send the string to the socket
	 */
	private PrintWriter socketOutput;
	/**
	 * The variable to connect to the server socket
	 */
	private Socket aSocket;
	/**
	 * A buffered reader for reading the input from the user
	 */
	private BufferedReader stdIn;
	/**
	 * A buffered reader for reading input from the server
	 */
	private BufferedReader socketIn;
	/**
	 * The constructor takes in a string for the serverName, and integer for the port number and instantiates this client's socket and buffered readers.
	 * @param serverName is a string for the server name
	 * @param portNumber is an integer for the port number
	 */
	public DateClient(String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOutput = new PrintWriter((aSocket.getOutputStream()),true);
		}catch(IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	/**
	 * This method prompts the user for inputs, and sends requests to the server, as well as receives information from the server.  This
	 * method runs continuously until the user enters 'QUIT' where it would terminate this client as well as prompt the server
	 * to terminate as well.
	 */
	public void communicate() {
		String line = "";
		String response = "";
		while(true) {
			try {
				System.out.println("Please select an option (DATE/TIME)");
				line = stdIn.readLine();
				socketOutput.println(line);
				if(line.equals("QUIT")) {
					break;
				}
				response = socketIn.readLine();
				System.out.println(response);
			}catch(IOException e){
				System.out.println("Sending Error: " + e.getMessage());
				break;
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOutput.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}
	/**
	 * This is the main method that creates an instance of the client object and inputs the port name and id.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateClient dClient = new DateClient("localhost",9090);
		dClient.communicate();
	}

}
