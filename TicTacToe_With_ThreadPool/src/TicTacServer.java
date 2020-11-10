import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This is the server class that connects the client to the back end and acts as a pipeline to transfer information.  It also creates a threadpool that allows two clients to connect
 * at each instance.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9, 2020
 *
 */
public class TicTacServer {
	/**
	 * The two sockets for each player
	 */
	private Socket socket1;
	private Socket socket2;
	/**
	 * The socket for the server
	 */
	private ServerSocket serverSocket;
	/**
	 * The writers and readers for the two clients
	 */
	private PrintWriter socket1Out;
	private PrintWriter socket2Out;
	private BufferedReader socket1In;
	private BufferedReader socket2In;
	/**
	 * Executor servers to create the thread pool
	 */
	private ExecutorService pool;
	/**
	 * The constructor constructs the serverSocket and instantiates the executor service to create a pool with two threads
	 */
	public TicTacServer() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(2);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Accepts the two clients and creates a new instance of the game when both players have connected
	 */
	public void runServer() {
		try {
			while(true) {//to keep accepting clients
				socket1 = serverSocket.accept();
				socket2 = serverSocket.accept();
				System.out.println("Server is running...");
				socket1In = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				socket2In = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				socket1Out = new PrintWriter(socket1.getOutputStream(), true);
				socket2Out = new PrintWriter(socket2.getOutputStream(), true);
				Game g = new Game(socket1Out, socket2Out, socket1In, socket2In);
				pool.execute(g);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();//need to shutdown pool when the server ends
		try {
			socket1In.close();
			socket2In.close();
			socket1Out.close();
			socket2Out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Creates an instance of the server object
	 * @param args
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException {
		TicTacServer myServer = new TicTacServer();
		myServer.runServer();
	}

}
