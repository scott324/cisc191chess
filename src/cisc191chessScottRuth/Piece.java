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
 * Model the things in common between the different chess pieces: the color, position, image, and the ability to move 
 */

public abstract class Piece
{
	private Square position;//Piece has-a position
	//The color of the piece isn't going to change throughout the game
	final boolean IS_WHITE;//Piece has-a IS_WHITE
	//This is the unicode character for the chess piece
	private char image;//Piece has-a image
	
	//This constructor sets up the position and color of the piece
	public Piece(Square square, boolean isItWhite)
	{
		position = square;
		IS_WHITE = isItWhite;
	}
	/**
	 * Purpose: Change the position of the piece
	 * 
	 * @param newColumn to replace the current one
	 * @param newRow to replace the current one
	 */
	public void setPosition(int newColumn, int newRow)
	{
		position.setSquare(newColumn, newRow);
	}
	/**
	 * Purpose: find out the position of the piece
	 * 
	 * @return the square the piece is on
	 */
	public Square getPosition()
	{
		return position;
	}
	/**
	 * Purpose: find out the color of the piece
	 * 
	 * @return true if the piece is white and false if it is black
	 */
	public boolean getWhetherWhite()
	{
		return IS_WHITE;
	}
	/**
	 * Purpose: Find out the image corresponding to the piece
	 * 
	 * @return image, the unicode character for the piece
	 */
	public char getImage()
	{
		return image;
	}
	/**
	 * Purpose: Change the image for the piece
	 * 
	 * @param newImage to be used
	 */
	public void setImage(char newImage)
	{
		image = newImage;
	}
	//Since each type of piece moves differently, they will each override this method differently
	public abstract Results checkMove(Square endPosition, boolean player1Goes);
}
