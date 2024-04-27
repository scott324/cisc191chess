package cisc191chessScottRuth;

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
 * Be thrown when a chess player attempts to make an illegal move
 */

public class IllegalMoveException extends Exception
{
	String errorMessage;//IllegalMoveException has-a errorMessage
	//This constructor takes in a String for the errorMessage
	public IllegalMoveException(String inputError)
	{
		errorMessage = inputError;
	}
	/**
	* Purpose: Find out the error message associated with the move
	* 
	* @return the message
	*/
	public String getError()
	{
		return errorMessage;
	}
}
