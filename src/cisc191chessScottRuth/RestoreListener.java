package cisc191chessScottRuth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

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
 * Restore a chess game using a file named with the number submitted by the user
 */

public class RestoreListener implements ActionListener //RestoreListener is ActionListener
{
	private Game game; //SaveListener has-a theGame
	private JTextField saveSelect; //SaveListener has-a saveSelect
	private GameView gameView; //RestoreListener has-a gameView
	
	//This constructor sets up the game being played, the text field where the user submits the file index,
	//and the game view that will need to be updated
	public RestoreListener(Game theGame, JTextField inputField, GameView theGameView)
	{
		game = theGame;
		saveSelect = inputField;
		gameView = theGameView;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//The index for the file will appear after the characters asking for input, so the substring begins at 47
		System.out.println(saveSelect.getText().substring(47));
		game.restoreGame(saveSelect.getText().substring(47));
		//Update the board to show the restored game
		gameView.updateBoard();
	}
}
