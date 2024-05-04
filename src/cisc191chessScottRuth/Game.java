package cisc191chessScottRuth;

import javax.swing.JOptionPane;

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
 * https://www.geeksforgeeks.org/java-joptionpane/
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
	private static final int squaresPerSide = 8; //Game has-a squaresPerSide
	Piece [][] board = new Piece[squaresPerSide+1][squaresPerSide+1];//Game has-many Board
	King whiteKing = new King(new Square(4,1),true);//Game has-a King
	King blackKing = new King(new Square(4,8), false);
	//startSquare stores the first square the user selects each time a move is made
	private Square startSquare; //Game has-a startSquare
	private boolean testingForCheck = false; //Game has-a testingForCheck
	//This constructor sets white as the starting player and creates all the piece objects as they are
	//at the beginning of a chess game
	public Game()
	{
		//White Pieces
		player1Goes = true;
		board[1][1] = new Rook(new Square(1,1),true);
		board[8][1] = new Rook(new Square(8,1),true);
		board[2][1] = new Knight(new Square(2,1),true);
		board[7][1] = new Knight(new Square(7,1),true);
		board[3][1] = new Bishop(new Square(3,1),true);
		board[6][1] = new Bishop(new Square(6,1),true);
		board[4][1] = whiteKing;
		board[5][1] = new Queen(new Square(5,1),true);
		for(int i=1;i<9;i++)
		{
			board[i][2] = new Pawn(new Square(i,2),true);
		}
		
		//Black pieces
		board[1][8] = new Rook(new Square(1,8),false);
		board[8][8] = new Rook(new Square(8,8),false);
		board[2][8] = new Knight(new Square(2,8),false);
		board[7][8] = new Knight(new Square(7,8),false);
		board[3][8] = new Bishop(new Square(3,8),false);
		board[6][8] = new Bishop(new Square(6,8),false);
		board[4][8] = blackKing;
		board[5][8] = new Queen(new Square(5,8),false);
		for(int i=1;i<squaresPerSide+1;i++)
		{
			board[i][7] = new Pawn(new Square(i,7),false);
		}
	}
	/**
	* Purpose: Find out the number of squares on each edge of the board, which will always be 8
	* 
	* @return the number of squares (8)
	*/
	public static int getSquaresPerSide()
	{
		return squaresPerSide;
	}
	/**
	* Purpose: Find out if the move is possible based on the check performed by the piece
	* 
	* @return true if the move is possible, false otherwise
	*/
	public Piece [][] getBoard()
	{
		return board;
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
		return board[column][row];
	}
	/**
	* Purpose: Find out a particular piece on the board given the square it's on
	* 
	* @param square to look for the piece
	* @return the piece on that coordinates of that square
	*/
	public Piece getPieceAtSquare(Square square)
	{
		return board[square.getColumn()][square.getRow()];
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
		//First check if the piece being moved is owned by the player moving it
		if(pieceToMove.getWhetherWhite() != player1Goes)
		{
			if(!testingForCheck)
			{
				JOptionPane.showMessageDialog(null, "It's not your turn", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}
		//Uses the piece's checkMove method first. If that test fails, this one should too
		Results moveResults;
		try
		{
			moveResults = pieceToMove.checkMove(destination, player1Goes);
		}
		catch(IllegalMoveException e)
		{
			if(!testingForCheck)
			{
				JOptionPane.showMessageDialog(null, e.getError(), "Error", JOptionPane.ERROR_MESSAGE);
			}
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
					if(!testingForCheck)
					{
						JOptionPane.showMessageDialog(null, "A piece is in the way", "Error", JOptionPane.ERROR_MESSAGE);
					}
					return false;
				}
			}
		}
		//The destination square will be treated differently, since it can already be filled as long as it is by a piece of the other color
		if(getPieceAtSquare(destination)!=null)
		{
			if(getPieceAtSquare(destination).getWhetherWhite() == pieceToMove.getWhetherWhite())
			{
				if(!testingForCheck)
				{
					JOptionPane.showMessageDialog(null, "A piece is in the way", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
		
		//Temporarily switch which player can move to test if any moves could take the king
		player1Goes = !player1Goes;
		for(int i=1;i<squaresPerSide+1;i++)
		{
			for(int j=1;j<9;j++)
			{
				if(board[i][j] != null)
				{
					if(board[i][j].getWhetherWhite() != kingToCheck.getWhetherWhite() && testMovePath(board[i][j],kingToCheck.getPosition()))
					{
						player1Goes = !player1Goes;
						return true;
					}
				}
			}
		}
		player1Goes = !player1Goes;
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
		//If there is no piece on the start square, a NullPointerException is thrown and caught,
		//And the user is alerted
		if(board[start.getColumn()][start.getRow()]!=null)
		{
			if(testMovePath(getPieceAtSquare(start), destination))
			{
				//If the move is possible, perform it, 
				//but store any piece that used to be on the destination square in case the move needs to be undone
				Piece backup = board[destination.getColumn()][destination.getRow()];
				board[start.getColumn()][start.getRow()].setPosition(destination.getColumn(), destination.getRow());
				board[destination.getColumn()][destination.getRow()] = board[start.getColumn()][start.getRow()];
				board[start.getColumn()][start.getRow()] = null;
				//If the move leaves the player in check, it will have to be undone and the user alerted
				testingForCheck = true;
				if(checkForCheck())
				{
					board[destination.getColumn()][destination.getRow()].setPosition(start.getColumn(),start.getRow());
					board[start.getColumn()][start.getRow()]=board[destination.getColumn()][destination.getRow()];
					board[destination.getColumn()][destination.getRow()]=backup;
					JOptionPane.showMessageDialog(null, "This move would place you in check", "Error", JOptionPane.ERROR_MESSAGE);
				}else
				{
					//If the move does work, it is now the other player's turn
					player1Goes = !player1Goes;
				}
				testingForCheck = false;
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Starting square must have a piece on it", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	* Purpose: Handle what should happen when the user clicks on a square
	* 
	* @param chosenSquare clicked by the user
	*/
	public void selectSquare(Square chosenSquare)
	{
		//If no squares are currently selected, then the chosen square becomes selected as the starting square
		if(startSquare == null)
		{
			startSquare = chosenSquare;
		}
		else if(chosenSquare != startSquare)//The end square selected must be different from the start square
		{
			//If a one square is already selected, the chosen square is selected as the destination and the move can be made
			move(startSquare, chosenSquare);
			//After the move, reset the start square so a new move can be made
			startSquare = null;
		}
	}
	public static void main(String [] args)
	{
		Game mainGame = new Game();
		//Testing the error message system
		//boolean errorTest = mainGame.testMovePath(mainGame.getPieceAtCoordinates(1, 2), new Square(2,2));
		System.out.print(mainGame.getPieceAtCoordinates(2, 1).getImage());
		mainGame.move(new Square(2,1), new Square(3,3));
		//mainGame.selectSquare(new Square(2,1));
		//mainGame.selectSquare(new Square(3,3));
	}
}
