package cisc191chessScottRuth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
 * Check if the active player is in checkmate when the button is pushed
 */

public class CheckmateListener implements ActionListener //CheckmateListener is ActionListener
{
	private Game game; //CheckmateListener has-a game
	
	//This constructor takes in what game is being played
	public CheckmateListener(Game theGame)
	{
		game = theGame;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//If the player is in checkmate, pop up a message declaring the winner
		if(game.checkForCheckmate())
		{
			if(game.getIfPlayer1())
			{
				JOptionPane.showMessageDialog(null, "Black wins", "Results", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "White wins", "Results", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		else
		{
			//If the player isn't in checkmate, alert the player of that
			JOptionPane.showMessageDialog(null, "You are not in checkmate", "Results", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
