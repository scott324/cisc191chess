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
 * Use the knight image and rules of knight movement
 */

public class Knight extends Piece
{
	//This constructor uses the superconstructor to set up the position and color, 
	//and then uses the appropriate unicode character for a knight, depending on the color
	public Knight(Square square, boolean isItWhite)
	{	
		super(square, isItWhite);
		if(isItWhite)
		{
			this.setImage('\u2658');
		}else
		{
			this.setImage('\u265E');
		}
	}
	/**
	 * Purpose: Check if a knight is moving on the right turn and in the right way
	 * 
	 * @param endPosition where the user has selected to put the knight
	 * @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	 * @return a Results object storing whether the check found the move to be possible,
	 * and if so, what space the knight finishes on.
	 */
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes)
	{
		//If the piece is not controlled by the active player, the move can't work
		if(this.getWhetherWhite() != player1Goes)
		{
			return new Results(false);
			//The knight can move 3 rows over and 1 column over in the positive or negative
		}else if(Math.abs(endPosition.getRow()-this.getPosition().getRow()) == 3)
		{
			if(Math.abs(endPosition.getColumn()-this.getPosition().getColumn()) == 1)
			{
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				passedSquares.add(endPosition);
				return new Results(passedSquares, true);
			}
			//Or the knight can move 3 columns over and 1 row over in the positive or negative
		}else if(Math.abs(endPosition.getColumn()-this.getPosition().getColumn()) == 3)
		{
			if(Math.abs(endPosition.getRow()-this.getPosition().getRow()) == 1)
			{
				ArrayList <Square> passedSquares = new ArrayList <Square>();
				passedSquares.add(endPosition);
				return new Results(passedSquares, true);
			}
		}
		//If the move is not one of the two types above, (3 and 1 or 1 and 3) it cannot be made
		return new Results(false);
	}
}
