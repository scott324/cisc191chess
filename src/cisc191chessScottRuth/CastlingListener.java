package cisc191chessScottRuth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* 
 * Lead Author(s):
 * @Scott Ruth; 5550080094
 * 
 * Other contributors:
 * none
 * 
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * Gaddis, T. (2015). Starting out with Java: From control structures through objects. Addison-Wesley. 
 * 
 * 
 * Version/date: 1.0
 * 
 * Responsibilities of class:
 * Attempt to castle when one of the castling buttons is pressed in the chess game
 */

public class CastlingListener implements ActionListener //CastlingListener is ActionListener
{
	private Game game;//CastlingListener has-a game
	private GameView gameView; //CastlingListener has-a gameView
	private boolean ifLeftSide; //CastlingListener has-a ifLeftSide
	
	//This constructor takes in a game and square for the listener and sets them accordingly
	public CastlingListener(Game inputGame, GameView inputView, boolean leftSide)
	{
		game = inputGame;
		gameView = inputView;
		ifLeftSide = leftSide;
	}
	/**
	* Purpose: Attempt to castle in the correct direction when the button is pushed, and then update the board
	* 
	*/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		game.castle(ifLeftSide);
		gameView.updateBoard();
	}

}
