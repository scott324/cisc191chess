package cisc191chessScottRuth;

import java.util.ArrayList;

public class King extends Piece
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
	* Purpose: Check if a king is moving on the right turn and in the right way
	* 
	* @param endPosition where the user has selected to put the queen
	* @param player1Goes if this is true, then only white pieces can move. Otherwise, only black pieces can move
	* @return a Results object storing whether the check found the move to be possible,
	* and if so, what spaces the king would pass through.
	*/
	@Override
	public Results checkMove(Square endPosition, boolean player1Goes)
	{
		//If the piece is not controlled by the active player, the move can't work
		if(this.getWhetherWhite() != player1Goes)
		{
			return new Results(false);
			//To be a possible move for a king, the destination can't be more than one space away in any direction
		}else if(Math.abs(endPosition.getColumn() - this.getPosition().getColumn()) <= 1 && Math.abs(endPosition.getRow()-this.getPosition().getRow()) <= 1)
		{
			ArrayList <Square> passedSquares = new ArrayList <Square>();
			//The only space passed through is the destination
			passedSquares.add(endPosition);
			return new Results(passedSquares, true);
		}else
		{
			return new Results(false);
		}
	}
}
