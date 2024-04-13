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
 * Store the row and column of a space on the chess board
 * Also serves as a temporary tester of the different piece objects
 */

public class Square
{
	private int row = 0;//Square has-a row
	private int column = 0;//Square has-a column
	
	//This constructor sets up the values for row and column
	public Square(int inColumn, int inRow)
	{
		column = inColumn;
		row = inRow;	
	}
	/**
	 * Purpose: Change the position of the square
	 * 
	 * @param newColumn to replace the current one
	 * @param newRow to replace the current one
	 */
	public void setSquare(int newColumn, int newRow)
	{
		column = newColumn;
		row = newRow;
	}
	/**
	 * Purpose: Find out the row the square is in
	 * 
	 * @return the row of the square
	 */
	public int getRow()
	{
		return row;
	}
	/**
	 * Purpose: Find out the column the square is in
	 * 
	 * @return the column of the square
	 */
	public int getColumn()
	{
		return column;
	}
	//This method tests various things about the classes I have created so far
	public static void main(String [] args)
	{
		Bishop whiteBishop = new Bishop(new Square(3,4), true);
		//This should print out the bishop image 
		System.out.println(whiteBishop.getImage());
		//This should be true, since the bishop is white
		System.out.println(whiteBishop.getWhetherWhite());
		//Test out moving to 4,4 - this shouldn't work
		Results badSquareResults = whiteBishop.checkMove(new Square(4,4), true);
		System.out.println(badSquareResults.getPossible());
		//Testing out moving on the wrong turn, but to a correct square
		Results badTurnResults = whiteBishop.checkMove(new Square(4,5), false);
		System.out.println(badTurnResults.getPossible());
		//Testing out a possible move and returning the squares that will need to be passed through for the move
		Results goodResults = whiteBishop.checkMove(new Square(1,6), true);
		System.out.println(goodResults.getPossible());
		System.out.println(goodResults.getStringOfSquares());
		
		Pawn blackPawn = new Pawn(new Square(1,7), false);
		//This should print out the pawn image 
		System.out.println(blackPawn.getImage());
		//This should be false, since the pawn is black
		System.out.println(blackPawn.getWhetherWhite());
		//Test out moving to 1,8 - this shouldn't work
		Results pawnBadSquareResults = blackPawn.checkMove(new Square(1,8), false);
		System.out.println(pawnBadSquareResults.getPossible());
		//Testing out a possible move and returning the squares that will need to be passed through for the move
		Results pawnGoodResults = blackPawn.checkMove(new Square(1,6), false);
		System.out.println(pawnGoodResults.getPossible());
		System.out.println(pawnGoodResults.getStringOfSquares());
		
		Knight blackKnight = new Knight(new Square(6,7), false);
		//This should print out the knight image
		System.out.println(blackKnight.getImage());
		//This should be false, since the knight is black
		System.out.println(blackKnight.getWhetherWhite());
		//Test out moving to 2,8 - this shouldn't work
		Results knightBadSquareResults = blackKnight.checkMove(new Square(2,8), false);
		System.out.println(knightBadSquareResults.getPossible());
		//Testing out a possible move and returning the squares that will need to be passed through for the move
		Results knightGoodResults = blackKnight.checkMove(new Square(7,4), false);
		System.out.println(knightGoodResults.getPossible());
		System.out.println(knightGoodResults.getStringOfSquares());
	}
}
