package cisc191chessScottRuth;

import java.lang.Math;
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
 * Use the bishop image and rules of bishop movement
 */

public class Bishop extends Piece
{
	//This constructor uses the superconstructor to set up the position and color, 
	//and then uses the appropriate unicode character for a bishop, depending on the color
	public Bishop(Square square, boolean isItWhite)
	{	
		super(square, isItWhite);
		if(isItWhite)
		{
			this.setImage('\u2657');
		}else
		{
			this.setImage('\u265D');
		}
	}
	/**
	 * Purpose: Check if a bishop is moving on the right turn and in the right way
	 * 
	 * @param endPosition where the user has selected to put the bishop
	 * @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	 * @return a Results object storing whether the check found the move to be possible,
	 * and if so, what spaces the bishop would pass through.
	 */
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes) throws IllegalMoveException
	{
		//If the piece is not controlled by the active player, the move can't work
		if(this.getWhetherWhite() != player1Goes)
		{
			throw new IllegalMoveException("It's not your turn");
			//To be a possible move for a bishop, the destination must be the same distance away in rows as in columns
		}else if(Math.abs(endPosition.getColumn() - this.getPosition().getColumn()) != Math.abs(endPosition.getRow()-this.getPosition().getRow()))
		{
			throw new IllegalMoveException("Bishop must move within its diagonal");
		}else
		{
			//rowFactor is 1 if the move increases the row number of the bishop and -1 if it decreases it
			int rowFactor = (endPosition.getRow()-this.getPosition().getRow())/Math.abs(endPosition.getRow()-this.getPosition().getRow());
			//columnFactor is 1 if the move increases the column number of the bishop and -1 if it decreases it
			int columnFactor = (endPosition.getColumn()-this.getPosition().getColumn())/Math.abs(endPosition.getColumn() - this.getPosition().getColumn());
			//This will store the squares that will be passed to the Results object that is returned
			ArrayList <Square> passedSquares = new ArrayList <Square>();
			//Each space passed through will be one rowFactor and one columnFactor farther from the starting position
			//These spaces are each added to the array list from closest to farthest
			for(int i=1; i <= Math.abs(endPosition.getRow()-this.getPosition().getRow());i++)
			{

					passedSquares.add(new Square(this.getPosition().getColumn()+i*columnFactor,this.getPosition().getRow()+i*rowFactor));
			}
			return new Results(passedSquares, true);
		}
	}

}
