package cisc191chessScottRuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
	//These two booleans ensure no error messages appear while the program is running tests which don't involve user input
	private boolean testingForCheck = false; //Game has-a testingForCheck
	private boolean testingForCheckmate = false; //Game has-a testingForCheck
	//This constructor sets white as the starting player and creates all the piece objects as they are
	//at the beginning of a chess game
	public Game()
	{
		//White Pieces
		setUpBoard();
	}
	/**
	* Purpose: Set the board according to the rules of chess
	* 
	*/
	public void setUpBoard()
	{
		player1Goes = true;
		//White pieces
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
		
		//Blank squares
		for(int i=1; i<=squaresPerSide; i++)
		{
			for(int j=3; j<=squaresPerSide-2;j++)
			{
				board[i][j]=null;
			}
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
		//This test specifically for a diagonal move from a pawn
		//For this to work, the following must all be true:
		//*The piece being moved is a pawn
		//*It is being moved to a column one to the left or right
		//*There is a piece on the destination square, and that piece is of the opposite color as the moving piece
		if(pieceToMove.getClass()==Pawn.class && Math.abs(destination.getColumn()-pieceToMove.getPosition().getColumn()) == 1 && 
				board[destination.getColumn()][destination.getRow()] != null &&
				board[destination.getColumn()][destination.getRow()].getWhetherWhite() != pieceToMove.getWhetherWhite())
		{
			//If the pawn is white, it should be moving forward relative to the row numbering
			if(pieceToMove.getWhetherWhite())
			{
				if(destination.getRow() - pieceToMove.getPosition().getRow() == 1)
				{
					return true;
				}
			//If the pawn is black, it should be moving backward relative to the row numbering
			}else if(destination.getRow() - pieceToMove.getPosition().getRow() == -1)
			{
				return true;
			}
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
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
		//If the piece being moved is a pawn, though, a piece of any color can block it
		if(getPieceAtSquare(destination)!=null)
		{
			if(getPieceAtSquare(destination).getWhetherWhite() == pieceToMove.getWhetherWhite() || pieceToMove.getClass() == Pawn.class)
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
					if(!testingForCheckmate)
					{
						JOptionPane.showMessageDialog(null, "This move would place you in check", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else
				{
					//If a move works, then the player is not in checkmate and the test can end. The piece needs to be put back, though
					if(testingForCheckmate)
					{
						board[destination.getColumn()][destination.getRow()].setPosition(start.getColumn(),start.getRow());
						board[start.getColumn()][start.getRow()]=board[destination.getColumn()][destination.getRow()];
						board[destination.getColumn()][destination.getRow()]=backup;
						testingForCheckmate = false;
					}
					else
					{
						//If the move does work and this is not a check, it is now the other player's turn and the piece that moved
						//should be marked as having moved
						player1Goes = !player1Goes;
						board[destination.getColumn()][destination.getRow()].setMoved();
					}
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
	/**
	* Purpose: Attempt to castle
	* 
	* @param leftSide, which is true if it is a left side castle and false if it is right side castle
	*/
	public void castle(boolean leftSide)
	{
		//If white is moving, the castling will happen in row 1
		//If black is moving, the castling will happen in row 8
		int row;
		if(player1Goes)
		{
			row = 1;
		}else
		{
			row = 8;
		}
		if(leftSide)
		{
			//The king and rook must be in their squares and never have moved before 
			if(board[4][row] != null && board[1][row] != null && !board[4][row].getIfMoved() && !board[1][row].getIfMoved())
			{
				//the spaces between must be clear
				if(board[2][row] == null && board[3][row] == null)
				{
					//The player cannot castle out of, through, or into check
					testingForCheck = true;
					if(!checkForCheck())
					{
						board[3][row] = board[4][row];
						board[4][row].setPosition(3, row);
						board[4][row] = null;
						if(!checkForCheck())
						{
							board[2][row] = board[3][row];
							board[3][row].setPosition(2, row);
							board[3][row] = board[1][row];
							board[1][row].setPosition(3, row);
							board[1][row] = null;
							if(!checkForCheck())
							{
								//At this point, the move is confirmed to work, so the turn is passed and pieces have now moved
								player1Goes = !player1Goes;
								board[2][row].setMoved();
								board[3][row].setMoved();
							}else
							{
								board[4][row] = board[2][row];
								board[2][row].setPosition(4, row);
								board[1][row] = board[3][row];
								board[3][row].setPosition(1, row);
								JOptionPane.showMessageDialog(null, "Cannot castle into check", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}else
						{
							board[4][row] = board[3][row];
							board[3][row].setPosition(4, row);
							board[3][row] = null;
							JOptionPane.showMessageDialog(null, "Cannot castle through check", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}else
					{
						JOptionPane.showMessageDialog(null, "Cannot castle out of check", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else
				{
					JOptionPane.showMessageDialog(null, "There is a piece in the way", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}else
			{
				JOptionPane.showMessageDialog(null, "The rook and the king can't have moved before", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}else
		{
			//The king and rook must be in their squares and never have moved before
			if(board[4][row] != null && board[4][row] != null && !board[4][row].getIfMoved() && !board[8][row].getIfMoved())
			{
				//The spaces between must be clear
				if(board[5][row] == null && board[6][row] == null && board[7][row] == null)
				{
					//The player cannot castle out of, through, or into check
					testingForCheck = true;
					if(!checkForCheck())
					{
						board[5][row] = board[4][row];
						board[4][row].setPosition(5, row);
						board[4][row] = null;
						if(!checkForCheck())
						{
							board[6][row] = board[5][row];
							board[5][row].setPosition(6, row);
							board[5][row] = board[8][row];
							board[8][row].setPosition(5, row);
							board[8][row] = null;
							if(!checkForCheck())
							{
								//At this point, the move is confirmed to work, so turn is passed and pieces have now moved
								player1Goes = !player1Goes;
								board[6][row].setMoved();
								board[5][row].setMoved();
								}else
								{
									board[4][row] = board[2][row];
									board[2][row].setPosition(4, row);
									board[1][row] = board[3][row];
									board[3][row].setPosition(1, row);
									JOptionPane.showMessageDialog(null, "Cannot castle into check", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}else
							{
								board[4][row] = board[5][row];
								board[5][row].setPosition(4, row);
								board[5][row] = null;
								JOptionPane.showMessageDialog(null, "Cannot castle through check", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}else
						{
							JOptionPane.showMessageDialog(null, "Cannot castle out of check", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}else
					{
						JOptionPane.showMessageDialog(null, "There is a piece in the way", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else
				{
					JOptionPane.showMessageDialog(null, "The rook and the king can't have moved before", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		testingForCheck = false;
	}
	/**
	* Purpose: Write the details of the game to a file in a way that the game may be reconstructed later
	* 
	* @param fileIndex of the file that will hold the details
	*/
	public void saveGame(String fileIndex)
	{
		PrintWriter gameSaver = null;
		try
		{
		    gameSaver = new PrintWriter("savedGame" + fileIndex);
		    
		    //The file will need to include whose turn it is
		    gameSaver.println(player1Goes);
		    
		    //For each square on the board, note if it is empty with an n
		    //If it does have a piece, note what it looks like and where on the board it started
		    //so it can be moved from there when the game is restored
		    for(int i=1; i<= squaresPerSide; i++)
		    {
		    	for(int j =1; j<= squaresPerSide; j++)
		    	{
		    		if(board[i][j] == null)
		    		{
		    			gameSaver.println(0);
		    		}
		    		else
		    		{
		    			gameSaver.println(board[i][j].getStartColumn());
		    			gameSaver.println(board[i][j].getStartRow());
		    		}
		    	}
		    }
		} 
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Unable to write to file", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			//The only reason not to close the file writer is if it was left null to begin with
			//In any other situation, the file writer will need to be closed, thus why this is in a finally block
			if(gameSaver!=null)
			{
				gameSaver.close();
			}
		}
	}
	/**
	* Purpose: Restore a game using the details stored in a file
	* 
	* @param fileIndex of the file being used to restore the game
	*/
	public void restoreGame(String requestedIndex)
	{
		//Since the pieces will be found using their starting positions, they must be in their starting positions
		//for the restoration to work
		setUpBoard();
		Scanner gameScan = null;
		try
		{
			//Look for the file with the details of the game and set up a scanner for it
			File saveFile = new File("savedGame"+ requestedIndex);
			gameScan = new Scanner(saveFile);
			//The first thing in the file should say whose turn it is
			player1Goes = gameScan.nextBoolean();
			
			//These will store the where a piece on the restored board should be moved from
			int pieceColumn;
			int pieceRow;
			ArrayList <Square> blankSquares = new ArrayList <Square>();
			for(int i=1; i<= squaresPerSide; i++)
		    {
		    	for(int j =1; j<= squaresPerSide; j++)
		    	{
		    		pieceColumn = gameScan.nextInt();
		    		if(pieceColumn == 0)
		    		{
		    			//If the column is 0, the square should be empty. 
		    			//If it is emptied now, however, then that might remove a piece which will be used later in the restoration.
		    			//Thus, the square to be emptied is noted to be dealt with later
		    			blankSquares.add(new Square(i, j));
		    		}
		    		else
		    		{
		    			//If there is a piece on the square, it can be brought over from its starting position
		    			pieceRow = gameScan.nextInt();
		    			board[i][j] = board[pieceColumn][pieceRow];
		    			board[pieceColumn][pieceRow].setPosition(i, j);
		    		}
		    	}
		    }
			//Now that the restoration is complete, each of the squares that needs to be emptied can be emptied
			for(int i = blankSquares.size()-1; i>=0; i--)
			{
				board[blankSquares.get(i).getColumn()][blankSquares.get(i).getRow()] = null;
			}
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Unable to access file", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			//The only reason not to close the scanner is if it was left null to begin with
			//In any other situation, the scanner will need to be closed, thus why this is in a finally block
			if(gameScan!=null)
			{
				gameScan.close();
			}
		}
	}
	/**
	* Purpose: Find out if the active player is in checkmate
	* 
	* @return true if the player is in checkmate, otherwise false
	*/
	public boolean checkForCheckmate()
	{
		testingForCheckmate = true;
		//For every piece on the board controlled by the active player, check all of its possible moves
		//If it can move successfully, the player is not in checkmate
		for(int i=1; i<=squaresPerSide; i++)
		{
			for(int j=1; j<=squaresPerSide; j++)
			{
				if(board[i][j] != null)
				{
					if(board[i][j].getWhetherWhite() == player1Goes)
					{
						//To check all moves possible for the piece board[i][j], go through each other square on the board
						//and see if the piece can move there
						for(int k=1; k<=squaresPerSide; k++)
						{
							for(int n=1; n<=squaresPerSide; n++)
							{
								if(k==i&&j==n)
								{
									//no need to check moving a piece to its own square
								}
								else
								{
									//Even though this isn't a test for check, it's still best to avoid as many error messages as
									//possible, so turn on testingForCheck
									testingForCheck = true;
									move(new Square(i,j), new Square(k,n));
									if(!testingForCheckmate)
									{
										testingForCheck = false;
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		testingForCheck = false;
		testingForCheckmate = false;
		return true;
	}
}
