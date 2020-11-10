import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * This is the GUI client that connects to the server as well as builds a graphical user interface which receives and sends commands.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @Since November 9, 2020
 *
 */
public class TicTacGUI extends JFrame implements ActionListener {
	/**
	 * There are the variables for a socket as well as the reader and writer objects
	 */
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	/**
	 * These are the variables specific to the player
	 */
	private String name;
	private char mark;
	private char oppMark;
	private int turn;
	private Board board;
	private int rowMove;
	private int colMove;
	/**
	 * The different Java gui objects used
	 */
	private JTextField nameField;
	private JTextField markField;
	private JTextArea msgField;
	private JButton b1,b2,b3,b4,b5,b6,b7,b8,b9 ;
	/**
	 * The constructor takes the serverName and portNumber and tries to connect to the server.  Then it calls the gui method to
	 * generate the gui
	 * 
	 * @param serverName
	 * @param portNumber
	 */
	public TicTacGUI(String serverName, int portNumber) {
		board = new Board();
		try {
			aSocket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(),true);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui();
	}
	/**
	 * This method generates the GUI, there is a base panel placed on a content pane.  The panel is divided into 
	 * a top panel and bottom panel.  The top panel has the grid for the buttons as well as the message window, and
	 * the bottom panel has the mark and the user name of the player.
	 */
	public void gui() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2));
		
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new GridLayout(1,2));
		
		JPanel msgPanel = new JPanel();
		msgField = new JTextArea(20, 30);
		JScrollPane scrollPane = new JScrollPane(msgField);
		msgField.setLineWrap(true);
		msgPanel.setLayout(new BorderLayout());
		msgPanel.add("Center", new JLabel("Message Window"));
		msgPanel.add("South",scrollPane);
		
		JPanel grid = new JPanel();
		grid.setPreferredSize(new Dimension(150, 150));
		grid.setLayout(new GridLayout(3, 3));
		b1 = new JButton("");
		b1.addActionListener(this);
		grid.add(b1);
		b2 = new JButton("");
		b2.addActionListener(this);
		grid.add(b2);
		b3 = new JButton("");
		b3.addActionListener(this);
		grid.add(b3);
		b4 = new JButton("");
		b4.addActionListener(this);
		grid.add(b4);
		b5 = new JButton("");
		b5.addActionListener(this);
		grid.add(b5);
		b6 = new JButton("");
		b6.addActionListener(this);
		grid.add(b6);
		b7 = new JButton("");
		b7.addActionListener(this);
		grid.add(b7);
		b8 = new JButton("");
		b8.addActionListener(this);
		grid.add(b8);
		b9 = new JButton("");
		b9.addActionListener(this);
		grid.add(b9);
		
		topPanel.add(grid);
		topPanel.add(msgPanel);
		
		JPanel markPanel = new JPanel();
		markPanel.setLayout(new FlowLayout());
		JLabel mark = new JLabel("Player: ");
		markField = new JTextField("",5);
		markPanel.add(mark);
		markPanel.add(markField);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		JLabel name = new JLabel("User Name: ");
		nameField = new JTextField("",20);
		nameField.addActionListener(this);
		namePanel.add(name);
		namePanel.add(nameField);
		
		botPanel.add(markPanel);
		botPanel.add(namePanel);
		panel.add(topPanel);
		panel.add(botPanel);
		
		contentPane.add("Center",panel);
		
		setTitle("Tic-Tac-Toe");
		setSize(650, 450);
		setVisible(true);
	}
	/**
	 * This method listens to all the actions performed for both the name text box
	 * as well as the buttons, and marks the buttons accordingly.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nameField) {
			name = nameField.getText();
			msgField.append("\nHello "+ name);
		}
		else if(turn == 1) {
			if(e.getSource() == b1) {
				setCell(0,0);
				b1.setText(Character.toString(mark));
			} else if(e.getSource() == b2) {
				setCell(0,1);
				b2.setText(Character.toString(mark));
			} else if(e.getSource() == b3) {
				setCell(0,2);
				b3.setText(Character.toString(mark));
			} else if(e.getSource() == b4) {
				setCell(1,0);
				b4.setText(Character.toString(mark));
			} else if(e.getSource() == b5) {
				setCell(1,1);
				b5.setText(Character.toString(mark));
			} else if(e.getSource() == b6) {
				setCell(1,2);
				b6.setText(Character.toString(mark));
			} else if(e.getSource() == b7) {
				setCell(2,0);
				b7.setText(Character.toString(mark));
			} else if(e.getSource() == b8) {
				setCell(2,1);
				b8.setText(Character.toString(mark));
			} else if(e.getSource() == b9) {
				setCell(2,2);
				b9.setText(Character.toString(mark));
			}
		}
	}
	/**
	 * This method sends the selected cell to the server and receives the cue to switch turns
	 * @param row is the row integer
	 * @param col is the column integer
	 */
	public void setCell(int row, int col) {
		try {
			rowMove = row;
			colMove = col;
			socketOut.println(rowMove);
			socketOut.println(colMove);
			turn = Integer.parseInt(socketIn.readLine());
			board.addMark(rowMove, colMove, this.mark);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method receives the necessary information to start the game such as the name, mark, and opponent's mark.  A while loop that delays
	 * the thread was included to wait for user to enter the name
	 */
	public void setUp() {
		msgField.append("\nPlease enter your name in the field below");
		while(name==null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socketOut.println(name);
		try {
			this.mark = socketIn.readLine().charAt(0);
			markField.setText(Character.toString(mark));
			this.oppMark = socketIn.readLine().charAt(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgField.append("\nMessage: Waiting for opponent to connect");
	}
	/**
	 * This method initially calls the setUp to get the information regarding the player, and continues the game while the game has
	 * not yet concluded.  The while loop continues until the game is over,
	 * which is indicated when the turn switches to three.  One means it's this players turn, and two means
	 * its their opponent's turn.  The while loop breaks after a winner or tie is declared. 
	 * A second while loop is also used to wait for the player to make their move.
	 */
	public void communicate() {
		msgField.append("Message: WELCOME TO THE GAME");
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
					msgField.append("\n"+this.name + ", select your next cell ");
					while(turn == 1) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if(turn ==2){
					int oppRowMove = Integer.parseInt(socketIn.readLine());
					int oppColMove = Integer.parseInt(socketIn.readLine());
					board.addMark(oppRowMove, oppColMove, this.oppMark);
					setOppCell(oppRowMove, oppColMove);
					System.out.println(this.name+" it is your turn");	
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
			msgField.append("\n"+socketIn.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSocket();
	}
	/**
	 * This method takes in the opponent's move and marks the cell on this board
	 * @param row is the opponent's row move
	 * @param col is the opponent's column move
	 */
	public void setOppCell(int row, int col) {
		if(row==0&&col==0) {
			b1.setText(Character.toString(oppMark));
		} else if(row==0&&col==1) {
			b2.setText(Character.toString(oppMark));
		} else if(row==0&&col==2) {
			b3.setText(Character.toString(oppMark));
		} else if(row==1&&col==0) {
			b4.setText(Character.toString(oppMark));
		} else if(row==1&&col==1) {
			b5.setText(Character.toString(oppMark));
		} else if(row==1&&col==2) {
			b6.setText(Character.toString(oppMark));
		} else if(row==2&&col==0) {
			b7.setText(Character.toString(oppMark));
		} else if(row==2&&col==1) {
			b8.setText(Character.toString(oppMark));
		} else if(row==2&&col==2) {
			b9.setText(Character.toString(oppMark));
		}
	}
	/**
	 * This method tries to close the sockets when the game is concluded.
	 */
	private void closeSocket() {
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Instantiates an instance of the Client GUI with the server name and port number
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args){
		try {
			TicTacGUI gui = new TicTacGUI("localhost", 9898);
			gui.communicate();
		} catch (Exception e){
			System.err.println(e);
		}
	}	
}