import java.io.*;

/**
 * The purpose of this class is to start off the game by creating instances of the players, board, and referee.   This class also implements the constants interface.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9 2020
 */
public class Game implements Constants,Runnable {
	/**
	 * Variables for the board and the referee
	 */
	private Board theBoard;
	private Referee theRef;
	/**
	 * Variables in order to read and write information to the server
	 */
	private PrintWriter socket1Out;
	private PrintWriter socket2Out;
	private BufferedReader socket1In;
	private BufferedReader socket2In;
	/**
	 * The constructor instantiates the different player's buffered readers and print writers
	 * @param socket1Out
	 * @param socket2Out
	 * @param socket1In
	 * @param socket2In
	 */
    public Game(PrintWriter socket1Out, PrintWriter socket2Out, BufferedReader socket1In, BufferedReader socket2In) {
        theBoard  = new Board();
        this.setSocket1Out(socket1Out);
        this.setSocket2Out(socket2Out);
        this.setSocket1In(socket1In);
        this.setSocket2In(socket2In);
	}
    /**
     * The sets the referee and calls the runTheGame() method. 
     * @param r is a referee object
     * @throws IOException
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	/**
	 * This is the main method to run the program, it creates instances of the referee and different players, as well as takes the inputs from the user and outputs the board display via
	 * command line interface 
	 * @param args
	 * @throws IOException
	 */
	public void run() {
		Referee theRef;
		Player xPlayer, oPlayer;
		theRef = new Referee();
		try {
			String name = socket1In.readLine();
			socket1Out.println(LETTER_X);
			xPlayer = new Player(name, LETTER_X,theBoard, socket1In, socket1Out);
			socket1Out.println(LETTER_O);
			xPlayer.setBoard(theBoard);
			theRef.setxPlayer(xPlayer);
		}catch(IOException e) {
			System.err.println("Error getting name of X player");
		}
		try {
			String name = socket2In.readLine();
			socket2Out.println(LETTER_O);
			oPlayer = new Player(name, LETTER_O, theBoard,socket2In, socket2Out);
			socket2Out.println(LETTER_X);
			oPlayer.setBoard(theBoard);
			theRef.setoPlayer(oPlayer);
		}catch(IOException e) {
			System.err.println("Error getting name of O player");
		}
        try {
			appointReferee(theRef);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public PrintWriter getSocket1Out() {
		return socket1Out;
	}
	public void setSocket1Out(PrintWriter socket1Out) {
		this.socket1Out = socket1Out;
	}
	public PrintWriter getSocket2Out() {
		return socket2Out;
	}
	public void setSocket2Out(PrintWriter socket2Out) {
		this.socket2Out = socket2Out;
	}
	public BufferedReader getSocket1In() {
		return socket1In;
	}
	public void setSocket1In(BufferedReader socket1In) {
		this.socket1In = socket1In;
	}
	public BufferedReader getSocket2In() {
		return socket2In;
	}
	public void setSocket2In(BufferedReader socket2In) {
		this.socket2In = socket2In;
	}
	

}
