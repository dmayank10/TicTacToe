import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * This is the client that takes inputs form the user, sends them to the server , and displays results from the opponent
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9, 2020
 *
 */
public class TicTacClient {
	/**
	 * The variables required to connect to the server, write variables, and receive inputs
	 */
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	private Scanner sc;
	/**
	 * This players name
	 */
	private String name;
	/**
	 * This players mark
	 */
	private char mark;
	/**
	 * opponent's mark
	 */
	private char oppMark;
	/**
	 * An integer to determine whose turn it is
	 */
	private int turn;
	/**
	 * A board object to display the board to this client
	 */
	private Board board;
	/**
	 * The constructor tries to connect to the server
	 * @param serverName
	 * @param portNumber
	 */
	public TicTacClient(String serverName, int portNumber) {
		sc = new Scanner(System.in);
		board = new Board();
		try {
			aSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(),true);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Gets the name, mark, and opponent's mark 
	 */
	public void setUp() {
		System.out.print("\nPlease enter player name: ");
		try {
			this.name= stdIn.readLine();
			while (name == null) {
			System.out.print("Please try again: ");
				this.name = stdIn.readLine();
			}
			socketOut.println(name);
			this.mark = socketIn.readLine().charAt(0);
			this.oppMark = socketIn.readLine().charAt(0);
			System.out.println("Message: Waiting for opponent to connect");
		}catch(IOException e) {
			System.err.println("Error reading player name");
		}
	}
	/**
	 * Displays the current game, asks users for the next move, and gets move inputs from the user.  
	 * This method also checks if the input is valid.  The while loop continues until the game is over,
	 * which is indicated when the turn switches to three.  One means it's this players turn, and two means
	 * its their opponent's turn.  The while loop breaks after a winner or tie is declared. 
	 */
	public void communicate() {
		board.display();
		System.out.println("Message: WELCOME TO THE GAME");
		setUp();
		try {
			turn = Integer.parseInt(socketIn.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(turn !=3) {
			try {
				if(turn == 1) {
					System.out.print(this.name + ", what row should your next " + this.mark + " be placed in? ");
					int rowMove = sc.nextInt();
					while (rowMove<0 || rowMove>2) {
						System.out.print(this.name+", please ensure the row index is from 0 to 2: ");
						rowMove = sc.nextInt();
					}
					System.out.print(this.name + ", what column should your next " + this.mark + " be placed in? ");
					int colMove = sc.nextInt();
					while (colMove<0 || colMove>2) {
						System.out.print(this.name+", please ensure the column index is from 0 to 2: ");
						colMove = sc.nextInt();
					}
					while(board.getMark(rowMove, colMove)!=' ') {
						System.out.print("This spot is already taken, "+this.name+", please enter a new row index: ");
						rowMove = sc.nextInt();
						System.out.print("Please enter a new column index: ");
						colMove = sc.nextInt();
					}
					socketOut.println(rowMove);
					socketOut.println(colMove);
					turn = Integer.parseInt(socketIn.readLine());
					board.addMark(rowMove, colMove, this.mark);
					board.display();
				}
				else if(turn ==2){
					int oppRowMove = Integer.parseInt(socketIn.readLine());
					int oppColMove = Integer.parseInt(socketIn.readLine());
					board.addMark(oppRowMove, oppColMove, this.oppMark);
					System.out.println(this.name+" it is your turn");
					board.display();
					turn = Integer.parseInt(socketIn.readLine());
				}
				else {
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			System.out.println(socketIn.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSocket();
	}
	/**
	 * Closes the connections once the game has concluded
	 */
	private void closeSocket() {
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *Instantiates an instance of the Client with the server name and port number
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTacClient player = new TicTacClient("localhost", 9898);//both need to be same port
		player.communicate();

	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int myTurn) {
		this.turn = myTurn;
	}
	public char getOppMark() {
		return oppMark;
	}
	public void setOppMark(char oppMark) {
		this.oppMark = oppMark;
	}
}
