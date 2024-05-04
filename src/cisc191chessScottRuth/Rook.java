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
 * Use the rook image and rules of rook movement
 */

public class Rook extends Piece
{
	//This constructor uses the superconstructor to set up the position and color, 
	//and then uses the appropriate unicode character for a rook, depending on the color
	public Rook(Square square, boolean isItWhite)
	{	
		super(square, isItWhite);
		if(isItWhite)
			{
				this.setImage('\u2656');
			}else
			{
				this.setImage('\u265C');
			}
		}
		/**
		 * Purpose: Check if a rook is moving in the right way
		 * 
		 * @param endPosition where the user has selected to put the rook
		 * @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
		 * @return a Results object storing whether the check found the move to be possible,
		 * and if so, what spaces the rook would pass through.
		 */
		@Override
		public Results checkMove(Square endPosition, boolean player1Goes) throws IllegalMoveException
		{
			//To be a possible move for a rook, the destination must be in the same row or column as the current position
			if(endPosition.getRow() == this.getPosition().getRow())
			{
				//columnFactor is 1 if the move increases the column number of the rook and -1 if it decreases it
				int columnFactor = (endPosition.getColumn()-this.getPosition().getColumn())/Math.abs(endPosition.getColumn() - this.getPosition().getColumn());
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				//Each space passed through will be in the same row and one columnFactor over from the last
				//These spaces are each added to the array list from closest to farthest
				for(int i=1; i <= Math.abs(endPosition.getColumn()-this.getPosition().getColumn());i++)
				{
						passedSquares.add(new Square(this.getPosition().getColumn() + i*columnFactor, this.getPosition().getRow()));
				}
				return new Results(passedSquares, true);
			}else if(endPosition.getColumn() == this.getPosition().getColumn())
			{
				//rowFactor is 1 if the move increases the row number of the rook and -1 if it decreases it
				int rowFactor = (endPosition.getRow()-this.getPosition().getRow())/Math.abs(endPosition.getRow() - this.getPosition().getRow());
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				//Each space passed through will be in the same column and one rowFactor over from the last
				//These spaces are each added to the array list from closest to farthest
				for(int i=1; i <= Math.abs(endPosition.getRow()-this.getPosition().getRow());i++)
				{
						passedSquares.add(new Square(this.getPosition().getRow() + i*rowFactor, this.getPosition().getColumn()));
				}
				return new Results(passedSquares, true);
			}else
			{
				throw new IllegalMoveException("Rook must move within a single row or column");
			}
			
		}
}
