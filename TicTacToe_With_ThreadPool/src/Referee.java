/**
 * This is the referee class used to moderate the game, set the opponents of each player
 * display the initial board, and start the game with X going first.
 * 
 * The main purpose of this class is to implement the set the roles of each player and
 * start the game through displaying the board and asking for X player for his inputs.
 * 
 * @author Karlen Chow
 * @version 1.0
 * @since November 9, 2020
 */
public class Referee {
	/**
	 * This is the X player object
	 */
	private Player xPlayer;
	/**
	 * This is the O player object
	 */
	private Player oPlayer;
	/**
	 * This is the default constructor method to generate an instance of the referee class
	 */
	public Referee() {}
	/**
	 * This method starts the game through establishing the opponent associations with
	 * each player, displaying the board, and asking X player for their move.
	 */
	public void runTheGame() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		xPlayer.play(1);
		oPlayer.play(2);
	}
	/**
	 * This method takes the O player object reference and links it to this instance of the referee.
	 * @param oPlayer is the player object for the O player
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	/**
	 * This method takes the X player object reference and links it to this instance of the referee.
	 * @param xPlayer is the player object for the X player
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
}
