import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * This is the player class for the game, it prompts the specified (X or O) user to enter the row and column index
 * where they would like their next move made, and checks if the game has been completed (a player wins or if the game is a
 * tie).
 * 
 * The purpose of this class is to establish the properties of each player and receive the proper inputs from each player
 * while also checking if the game is over.  It also detects who won the game and displays the proper outputs.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9, 2020
 *
 */
public class Player {
	/**
	 * This is the string for the name of the player
	 */
	private String name;
	/**
	 * This is the board object for the player to make their moves on
	 */
	private Board board;
	/**
	 * This is the opponent object for the other player object
	 */
	private Player opponent;
	/**
	 * This is the mark character (X or O) for this instance of the player
	 */
	private char mark;
	private int myTurn;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	/**
	 * The constructor for this class takes the name as a string and the mark as a character, and calls the appropriate
	 * setter methods for each.
	 * @param n is the string for the name
	 * @param m is the character for the mark
	 */
	public Player(String n, char m,Board b, BufferedReader socketIn, PrintWriter socketOut) {
		setName(n);
		setMark(m);
		setBoard(b);
		setSocketIn(socketIn);
		setSocketOut(socketOut);
	}
	/**
	 * This method checks if either this player or its opponent has won the game or if the game ended up in a tie, else
	 * it calls the makeMove() method to prompt the user to enter their next move.
	 */
	public void play(int turn) {
		this.myTurn = turn;
		if(board.xWins()) {
			myTurn = 3;
			socketOut.println(myTurn);
			opponent.socketOut.println(myTurn);
			String winner = this.mark=='X'?this.name:opponent.name;
			socketOut.println("GAME IS OVER: "+winner+" is the winner!");
			opponent.socketOut.println("GAME IS OVER: "+winner+" is the winner!");
		}
		else if(board.oWins()) {
			myTurn = 3;
			socketOut.println(myTurn);
			opponent.socketOut.println(myTurn);
			String winner = this.mark=='O'?this.name:opponent.name;
			socketOut.println("GAME IS OVER: "+winner+" is the winner!");
			opponent.socketOut.println("GAME IS OVER: "+winner+" is the winner!");
		}
		else if(board.isFull()) {
			myTurn = 3;
			socketOut.println(myTurn);
			opponent.socketOut.println(myTurn);
			socketOut.println("Tie");
			opponent.socketOut.println("Tie");
		}
		else {
			socketOut.println(myTurn);
			if(turn == 1) {
				opponent.play(2);
				makeMove();
			}
		}
	}
	/**
	 * This is the method that prompts the user to the row and column index corresponding to their move.  It also checks
	 * if the input from the user is between 0 to 2 as well as if the space is already taken.  If the input is correct, it 
	 * calls the board methods to add the mark to the board, display the result, and call the opponent's play method.
	 */
	public void makeMove() {
		try {
			int rowMove = Integer.parseInt(socketIn.readLine());
			int colMove = Integer.parseInt(socketIn.readLine());
			board.addMark(rowMove, colMove, this.mark);
			opponent.socketOut.println(rowMove);
			opponent.socketOut.println(colMove);
			this.myTurn = 2;
			opponent.play(1);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This is the setter method to set the opponent player to this player.
	 * @param p is the player object for the opponent
	 */
	public void setOpponent(Player p) {
		this.opponent = p;
	}
	/**
	 * This is the setter method to set the Board object to this player
	 * @param theBoard is the Board object
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}
	/**
	 * This is the getter method for the name
	 * @return the name for this player as a string 
	 */
	public String getName() {
		return name;
	}
	/**
	 * This is the setter method to set this player's name
	 * @param name is a string for this player's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This is the getter method to retrieve this player's mark character
	 * @return this player's mark character
	 */
	public char getMark() {
		return mark;
	}
	/**
	 * This is the setter method to set this player's mark for the game
	 * @param mark is a character for this player's mark
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}
	public int isMyTurn() {
		return myTurn;
	}
	public void setMyTurn(int myTurn) {
		this.myTurn = myTurn;
	}
	private void setSocketOut(PrintWriter socketOut2) {
		this.socketOut = socketOut2;
	}
	private void setSocketIn(BufferedReader socketIn2) {
		this.socketIn = socketIn2;
	}
}
