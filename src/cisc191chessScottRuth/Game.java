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
 * Store the information about a chess game, test moves, and save the game if instructed
 * So far only does the first two
 */

public class Game
{
	//This stores if it is the turn of the person playing white 
	private boolean player1Goes; //Game has-a player1Goes
	//The board has an extra row and an extra column which are never filled in order to avoid index confusion
	//and because it is useful to have squares which are known to be empty
	Piece [][] Board = new Piece[9][9];//Game has-many Board
	King whiteKing = new King(new Square(4,1),true);//Game has-a King
	King blackKing = new King(new Square(4,8), false);
	//This constructor sets white as the starting player and creates all the piece objects as they are
	//at the beginning of a chess game
	public Game()
	{
		player1Goes = true;
		Board[1][1] = new Rook(new Square(1,1),true);
		Board[8][1] = new Rook(new Square(8,1),true);
		Board[2][1] = new Knight(new Square(2,1),true);
		Board[7][1] = new Knight(new Square(7,1),true);
		Board[3][1] = new Bishop(new Square(3,1),true);
		Board[6][1] = new Bishop(new Square(6,1),true);
		Board[4][1] = whiteKing;
		Board[5][1] = new Queen(new Square(5,1),true);
		for(int i=1;i<9;i++)
		{
			Board[i][2] = new Pawn(new Square(i,2),true);
		}
	}
	/**
	* Purpose: Find out if the move is possible based on the check performed by the piece
	* 
	* @return true if the move is possible, false otherwise
	*/
	public Piece [][] getBoard()
	{
		return Board;
	}
	/**
	* Purpose: Find out a particular piece on the board given its coordinates
	* 
	* @param column to look for the piece
	* @param row to look for the piece
	* @return the piece at that position
	*/
	public Piece getPieceAtCoordinates(int column, int row)
	{
		return Board[column][row];
	}
	/**
	* Purpose: Find out a particular piece on the board given the square it's on
	* 
	* @param square to look for the piece
	* @return the piece on that coordinates of that square
	*/
	public Piece getPieceAtSquare(Square square)
	{
		return Board[square.getColumn()][square.getRow()];
	}
	/**
	* Purpose: Find out if whose turn it is
	* 
	* @return true if it is white's turn and false if it is black's
	*/
	public boolean getIfPlayer1()
	{
		return player1Goes;
	}
	/**
	* Purpose: Check if a move is possible for a piece and if any pieces are in the way
	* 
	* @param pieceToMove which the user has selected
	* @param destination where the piece has been directed to go
	* @return false if the move is blocked our outside the piece's range, otherwise true
	*/
	public boolean testMovePath(Piece pieceToMove, Square destination)
	{
		//Uses the piece's checkMove method first. If that test fails, this one should too
		Results moveResults = pieceToMove.checkMove(destination, getIfPlayer1());
		if(!moveResults.getPossible())
		{
			return false;
		}
		//If the first check works, go through the squares that will need to be passed and check if they are blocked
		//The squares passed before the destination cannot have a piece of either color
		if(moveResults.getSquares().length > 1)
		{
			for(int i=moveResults.getSquares().length-2;i>-1;i--)
			{
				if(getPieceAtSquare(moveResults.getSquares()[i]) != null)
				{
					return false;
				}
			}
		}
		//The destination square will be treated differently, since it can already be filled as long as it is by a piece of the other color
		if(getPieceAtSquare(destination)!=null)
		{
			if(getPieceAtSquare(destination).getWhetherWhite() == pieceToMove.getWhetherWhite())
			{
				return false;
			}
		}
		return true;
	}
	/**
	* Purpose: Check if the player illegally put themselves into check 
	* 
	* @return true if the player whose turn it is is in check
	*/
	public boolean checkForCheck()
	{
		King kingToCheck;
		//Only the king of the player who just moved needs to be checked
		if(getIfPlayer1())
		{
			kingToCheck = whiteKing;
		}else
		{
			kingToCheck = blackKing;
		}
		//Go through each piece on the board. If it is the opposite color of the king being check and could move to the king's position
		//then the last player put themselves in check
		for(int i=1;i<9;i++)
		{
			for(int j=1;j<9;j++)
			{
				if(Board[i][j] != null)
				{
					if(Board[i][j].getWhetherWhite() != getIfPlayer1() && testMovePath(Board[i][j],kingToCheck.getPosition()))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	* Purpose: Move a piece, if possible
	* 
	* @param pieceToMove which the user has selected
	* @param destination where the piece has been directed to go
	*/
	public void move(Square start, Square destination)
	{
		if(testMovePath(getPieceAtSquare(start), destination))
		{
			//If the move is possible, perform it, 
			//but store any piece that used to be on the destination square in case the move needs to be undone
			Piece backup = Board[destination.getColumn()][destination.getRow()];
			Board[destination.getColumn()][destination.getRow()] = Board[start.getColumn()][start.getRow()];
			Board[start.getColumn()][start.getRow()] = null;
			//If the move leaves the player in check, it will have to be undone and the user alerted
			if(checkForCheck())
			{
				Board[start.getColumn()][start.getRow()]=Board[destination.getColumn()][destination.getRow()];
				Board[destination.getColumn()][destination.getRow()]=backup;
				//I will set up a way to alert the user when I make the rest of the GUI, this is just a reminder
				System.out.println("error message");
			}else
			{
				//If the move does work, it is now the other player's turn
				player1Goes = !player1Goes;
			}
		}else
		{
			//I will set up a way to alert the user when I make the rest of the GUI, this is just a reminder
			System.out.println("other error message");
		}
	}
	public static void main(String [] args)
	{
		//A test of how the board looks at the start of the game using the console
		Game mainGame = new Game();
		for(int i=1;i<3;i++)
		{
			for(int j=1;j<9;j++)
			{
				System.out.print(mainGame.getPieceAtCoordinates(j,i).getImage());
			}
			System.out.println();
		}
	}
}
