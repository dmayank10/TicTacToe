/**
 * This class contains the main computational methods behind the tic tac toe game such as drawing the board, marking the board after each turn, checking if there is a winner,
 * and clearing the board.  This class implements the methods within the constants class.
 * 
 * The purpose of this class is to become familiar with reviewing code and implementing methods within this class in the player and referee classes.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9, 2020
 *
 */
public class Board implements Constants {
	/**
	 * This is the 2D character array for the board
	 */
	private char theBoard[][];
	/**
	 * This is an integer for the count of how many moves (marks) have been made.  If it reaches 9, that means the board is full and the game is over.
	 */
	private int markCount;
	/**
	 * The constructor for this board class first sets the value of zero to the number of marks, then builds the 3x3 character board, setting each value as a space (SPACE_CHAR)
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	/**
	 * This is the method to return the mark in a given location on the board.
	 * @param row is an integer for the row index
	 * @param col is an integer for the column index
	 * @return a char for the mark in the specified coordinates
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
	/**
	 * This is the method that determines if the board is full.
	 * @return true if the number of marks reaches nine, and false if the number of marks is not nine
	 */
	public boolean isFull() {
		return markCount == 9;
	}
	/**
	 * This method calls the checkWinner method to check if the winner is the X player.
	 * @return true if the winner is the X player, false if winner is not X player
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}
	/**
	 * This method calls the checkWinner method to check if the winner is the O player.
	 * @return true if the winner is the O player, false if winner is not O player
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	/**
	 * This is the method calls the displayColumnHeaders, addHyphens, addSpaces, and getMark methods to draw the tic tac toe board.
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}
	/**
	 * This is the method to call to add a mark to the 2D character matrix.
	 * @param row is the integer for the index of the row
	 * @param col is the integer for the index of the column
	 * @param mark is the char for the mark which was made on the space
	 */
	public void addMark(int row, int col, char mark) {		
		theBoard[row][col] = mark;
		markCount++;
	}
	/**
	 * This is the method to reset the game, it decreases the markCount to zero and clears the marks on the tic tac toe board.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
	/**
	 * This is the method to call to check if there is a winner, that is, 3 of the same mark are consecutive horizontally, vertically, or diagonally:
	 * 1) checks if there are three of the given mark ordered consecutively in any row, if so, result is changed to 1
	 * 2) checks if there are three of the given mark ordered consecutively in any column, if so, result is changed to 1
	 * 3) checks if there are three of the given mark diagonally from top left to bottom right, if so, result is changed to 1
	 * 4) checks if there are three of the given mark diagonally from top right to bottom left, if so, result changed to 1
	 * @param mark is the character that the method checks if they won the game
	 * @return 0 if the mark did not and 1 if the mark won
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
	/**
	 * This method is called by the display method to print the column headers.
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}
	/**
	 * This method is called by the display method to print the hyphens separating each row.
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}
	/**
	 * This is the method called by the display method to print the lines separating the spaces in each row.
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
