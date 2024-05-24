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
 * Use the king image and rules of king movement
 */

public class King extends Piece //King is-a Piece
{
	public King(Square square, boolean isItWhite)
	{
		super(square, isItWhite);
		if(isItWhite)
		{
			this.setImage('\u2654');
		}else
		{
			this.setImage('\u265A');
		}
	}
	/**
	* Purpose: Check if a king is moving in the right way
	* 
	* @param endPosition where the user has selected to put the queen
	* @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	* @return a Results object storing whether the check found the move to be possible,
	* and if so, what spaces the king would pass through.
	 * @throws IllegalMoveException 
	*/
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes) throws IllegalMoveException
	{
		//To be a possible move for a king, the destination can't be more than one space away in any direction
		if(Math.abs(endPosition.getColumn() - this.getPosition().getColumn()) <= 1 && Math.abs(endPosition.getRow()-this.getPosition().getRow()) <= 1)
		{
			ArrayList <Square> passedSquares = new ArrayList <Square>();
			//The only space passed through is the destination
			passedSquares.add(endPosition);
			return new Results(passedSquares, true);
		}else
		{
			throw new IllegalMoveException("King must move 1 square at a time");
		}
	}
}
