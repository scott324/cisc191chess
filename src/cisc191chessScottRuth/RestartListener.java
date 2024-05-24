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
 * Restart a chess game to its initial position when commanded by the user
 */

public class RestartListener implements ActionListener //RestartListener is ActionListener
{
	private Game game; //RestartListener has-a game
	private GameView gameView; //RestartListener has-a gameView
	
	//This constructor sets up the game being played and the game view that will need to be updated
	public RestartListener(Game theGame, GameView theGameView)
	{
		game = theGame;
		gameView = theGameView;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//The setUpBoard method will put all the pieces in their starting positions and set the active player to white
		game.setUpBoard();
		//Update the board to show the new game
		gameView.updateBoard();
	}

}
