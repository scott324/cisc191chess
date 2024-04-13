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
 * Currently a work in progress to support the piece classes
 */

public class Results
{
	private Square[] passedSquares;
	private boolean possible;
	public Results(ArrayList <Square> whichSquaresPassed, boolean whetherPossible)
	{
		passedSquares = new Square[whichSquaresPassed.size()];
		passedSquares = whichSquaresPassed.toArray(passedSquares);
		possible = whetherPossible;
	}
	public Results(boolean whetherPossible)
	{
		possible = whetherPossible;
	}
	public boolean getPossible()
	{
		return possible;
	}
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
