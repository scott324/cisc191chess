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
 * Save a chess game in a file named with the number submitted by the user
 */

public class SaveListener implements ActionListener
{
	private Game game; //SaveListener has-a theGame
	private JTextField saveSelect; //SaveListener has-a saveSelect
	
	//This constructor sets up the game being played and the text field where the user submits the file index
	public SaveListener(Game theGame, JTextField inputField)
	{
		game = theGame;
		saveSelect = inputField;
	}
	/**
	* Purpose: Save the game in the correct file
	* 
	*/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//The index for the file will appear after the characters asking for input, so the substring begins at 33
		game.saveGame(saveSelect.getText().substring(33));
	}

}
