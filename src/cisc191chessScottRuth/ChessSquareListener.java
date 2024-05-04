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
 * https://www.geeksforgeeks.org/java-joptionpane/
 * 
 * Version/date: 1.0
 * 
 * Responsibilities of class:
 * Select the correct square on a chess board when the listener's button is pressed
 */

public class ChessSquareListener implements ActionListener
{
	private Game game;//ChessSquareListener has-a game
	private Square square;//ChessSquareListener has-a square
	private GameView gameView; //ChessSquareListener has-a gameView
	
	//This constructor takes in a game and square for the listener and sets them accordingly
	public ChessSquareListener(Game inputGame, GameView inputView, int column, int row)
	{
		game = inputGame;
		gameView = inputView;
		square = new Square(column, row);
	}
	/**
	* Purpose: Select the square associated with the listener
	* 
	*/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		game.selectSquare(square);
		gameView.updateBoard();
	}

}
