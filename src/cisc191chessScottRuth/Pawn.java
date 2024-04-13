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
 * Use the pawn image and rules of pawn movement
 */

public class Pawn extends Piece
{
	//This constructor uses the superconstructor to set up the position and color, 
	//and then uses the appropriate unicode character for a pawn, depending on the color
	public Pawn(Square square, boolean isItWhite)
	{
		super(square, isItWhite);
		if(isItWhite)
		{
			this.setImage('\u2659');
		}else
		{
			this.setImage('\u265F');
		}
	}
	/**
	 * Purpose: Check if a pawn is moving on the right turn and in the right way
	 * 
	 * @param endPosition where the user has selected to put the pawn
	 * @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	 * @return a Results object storing whether the check found the move to be possible,
	 * and if so, what space the pawn would land on.
	 */
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes)
	{
		//So far for the program, we assume that the pawn cannot leave its column
		if(endPosition.getColumn() != this.getPosition().getColumn())
		{
			return new Results(false);
		}
		//If it is player 1's turn, only white pawns can move
		//So far for the program, we assume that the pawn can only move one space at a time
		//The white pawn moves one row forward each move relative to the numbering system
		if(this.getWhetherWhite())
		{
			if(!player1Goes)
			{
				return new Results(false);
			}
			if(endPosition.getRow()==this.getPosition().getRow()+1)
			{
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				passedSquares.add(endPosition);
				return new Results(passedSquares, true);
			}
			return new Results(false);
		}
		//If the method has reached this point, then the pawn is black and can only move if it is player 2's turn and not player 1's
		//So far for the program, we assume that the pawn can only move one space at a time
		//The black pawn moves one row back each move relative to the numbering system
		if(player1Goes)
		{
			return new Results(false);
		}
		if(endPosition.getRow()==this.getPosition().getRow()-1)
		{
			ArrayList <Square> passedSquares = new ArrayList <Square>();
			passedSquares.add(endPosition);
			return new Results(passedSquares, true);
		}
		return new Results(false);
	}

}
