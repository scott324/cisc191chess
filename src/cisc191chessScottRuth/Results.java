package cisc191chessScottRuth;

import java.util.ArrayList;

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
 * Version/date: 1.0
 * 
 * Responsibilities of class:
 * Store the possibility of a move given the rules of a piece and the squares the piece would pass through
 */

public class Results
{
	private Square[] passedSquares; //Results has-many passedSquares
	private boolean possible; //Results has-a possible
	//This constructor takes in values for the passedSquares and whetherPossible
	public Results(ArrayList <Square> whichSquaresPassed, boolean whetherPossible)
	{
		passedSquares = new Square[whichSquaresPassed.size()];
		passedSquares = whichSquaresPassed.toArray(passedSquares);
		possible = whetherPossible;
	}
	/**
	* Purpose: Check if a king is moving on the right turn and in the right way
	* 
	* @param endPosition where the user has selected to put the queen
	* @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	* @return a Results object storing whether the check found the move to be possible,
	* and if so, what spaces the king would pass through.
	*/
	public Results(boolean whetherPossible)
	{
		possible = whetherPossible;
	}
	/**
	* Purpose: Find out if the move is possible based on the check performed by the piece
	* 
	* @return true if the move is possible, false otherwise
	*/
	public boolean getPossible()
	{
		return possible;
	}
	/**
	* Purpose: Find out what squares the piece will have to pass through
	* 
	* @return an array of the squares
	*/
	public Square[] getSquares()
	{
		return passedSquares;
	}
	/**
	* Purpose: Make a list of what squares the piece will have to pass through to make the move. Used just for testing purposes
	* 
	* @return a String listing the coordinates of each square passed through
	*/
	public String getStringOfSquares()
	{
		String list = "";
		for(int i=0; i<passedSquares.length; i++)
		{
			list += "(" + passedSquares[i].getColumn() + ", " + passedSquares[i].getRow() + ") ";
		}
		return list;
	}
}
