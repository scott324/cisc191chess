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
	 * Purpose: Check if a pawn is moving in the right way
	 * 
	 * @param endPosition where the user has selected to put the pawn
	 * @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	 * @return a Results object storing whether the check found the move to be possible,
	 * and if so, what space the pawn would land on.
	 */
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes) throws IllegalMoveException
	{
		//So far for the program, we assume that the pawn cannot leave its column
		if(endPosition.getColumn() != this.getPosition().getColumn())
		{
			throw new IllegalMoveException("Pawn must move in its column");
		}
		//If the pawn is white, it can only move one row forward each move relative to the numbering system,
		//Or two rows forward if it has not moved before
		if(this.getWhetherWhite())
		{
			if(endPosition.getRow()==this.getPosition().getRow()+1)
			{
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				passedSquares.add(endPosition);
				return new Results(passedSquares, true);
			}
			//If the pawn has not moved before, it can travel two spaces
			if(!this.getIfMoved())
			{
				if(endPosition.getRow()==this.getPosition().getRow()+2)
				{
					ArrayList <Square> passedSquares = new ArrayList <Square>();
					passedSquares.add(endPosition);
					return new Results(passedSquares, true);
				}
			}
			throw new IllegalMoveException("Pawn can only move one space");
		}
		//If the method has reached this point, then the pawn is black
		//The black pawn moves one row back each move relative to the numbering system
		//Or two rows back if it has not moved before
		if(endPosition.getRow()==this.getPosition().getRow()-1)
		{
			ArrayList <Square> passedSquares = new ArrayList <Square>();
			passedSquares.add(endPosition);
			return new Results(passedSquares, true);
		}
		//If the pawn has not moved before, it can travel two spaces
		if(!this.getIfMoved())
		{
			if(endPosition.getRow()==this.getPosition().getRow()-2)
			{
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				passedSquares.add(endPosition);
				return new Results(passedSquares, true);
			}
		}
		throw new IllegalMoveException("Pawn can only move one space");
	}
}
