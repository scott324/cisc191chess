package cisc191chessScottRuth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
 * https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/layout/box.html 
 *  
 * Version/date: 1.0
 * 
 * Responsibilities of class:
 * Handle the GUI of a chess game
 */

public class GameView extends JFrame
{
	private Game game; //GameView has-a game
	private String activePlayer = "Player 1 (white)"; //GameView has-a activePlayer
	private JPanel descriptionPanel; //GameView has-a descriptionPanel
	private JPanel optionsButtons; //GameView has-a optionsButtons
	private JPanel boardPanel; //GameView has-a boardPanel
	private JPanel castlingPanel; //GameView has-a castlingPanel
	private JLabel description; //GameView has-a description
	private JButton [][] board = new JButton [8][8]; //GameView has-a board
	private JButton saveButton; //GameView has-a saveButton
	private JButton restoreButton; //GameView has-a restoreButton
	private JButton checkmateButton; //GameView has-a checkmateButton
	private JButton leftCastle; // GameView has-a leftCastle
	private JButton rightCastle; //GameView has-a rightCastle
	
	public GameView(Game theGame)
	{
		//Use the game passed into the constructor
		game = theGame;
		//Set up some basic aspects of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		this.setTitle("Chess Game");
		this.setLayout(new BorderLayout());
		this.setSize(1300, 800);
		//The description goes in the description panel at the top of the display
		descriptionPanel = new JPanel();
		description = new JLabel(activePlayer + ", Click a starting and finishing square to move a piece");
		descriptionPanel.add(description);
		this.add(descriptionPanel, BorderLayout.NORTH);
		//The board panel contains an 8 by 8 grid of buttons corresponding to the squares on the chess board
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(Game.getSquaresPerSide(),Game.getSquaresPerSide()));
		for(int i = 0; i<Game.getSquaresPerSide(); i++)
		{
			for(int j = 0; j<Game.getSquaresPerSide(); j++)
			{
				//Only add an image if there is a piece on the square
				if(game.getPieceAtCoordinates(j+1, i+1) != null)
				{
					board[i][j] = new JButton(String.valueOf(game.getPieceAtCoordinates(j+1, i+1).getImage()));
					board[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 60));	
				}else
				{
					board[i][j] = new JButton("");
				}
				//If the coordinates of the square sum to an even number, the square should be white
				//Otherwise, it should be black, though gray helps the black pieces stand out better
				if((i+j)%2==0)
				{
					board[i][j].setBackground(Color.WHITE);
				}else
				{
					board[i][j].setBackground(Color.GRAY);
				}
				board[i][j].addActionListener(new ChessSquareListener(game,this, j+1,i+1));
				boardPanel.add(board[i][j]);
			}
		}
		this.add(boardPanel, BorderLayout.CENTER);
		//The buttons for saving, restoring, and checking for checkmate go in a panel on the right
		optionsButtons = new JPanel();
		optionsButtons.setLayout(new BoxLayout(optionsButtons, BoxLayout.PAGE_AXIS));
		saveButton = new JButton("Save game");
		optionsButtons.add(saveButton);
		restoreButton = new JButton("Restore a game");
		optionsButtons.add(restoreButton);
		checkmateButton = new JButton("Check for checkmate");
		optionsButtons.add(checkmateButton);
		this.add(optionsButtons, BorderLayout.EAST);
		//The buttons for castling go in the panel at the bottom
		castlingPanel = new JPanel();
		castlingPanel.setLayout(new BoxLayout(castlingPanel, BoxLayout.LINE_AXIS));
		leftCastle = new JButton("Castle kingside");
		leftCastle.addActionListener(new CastlingListener(game,this,true));
		castlingPanel.add(leftCastle);
		rightCastle = new JButton("Catle queenside");
		castlingPanel.add(rightCastle);
		rightCastle.addActionListener(new CastlingListener(game,this,false));
		this.add(castlingPanel, BorderLayout.SOUTH);
	}
	/**
	* Purpose: Change the interface to reflect any changes to the board
	* 
	*/
	public void updateBoard()
	{
		for(int i = 0; i<Game.getSquaresPerSide(); i++)
		{
			for(int j = 0; j<Game.getSquaresPerSide(); j++)
			{
				//Only add an image if there is a piece on the square
				if(game.getPieceAtCoordinates(j+1, i+1) != null)
				{
					board[i][j].setText(String.valueOf(game.getPieceAtCoordinates(j+1, i+1).getImage()));
					board[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 60));
				}else
				{
					board[i][j].setText("");
				}
			}
		}
	}
	public static void main(String args [])
	{
		Game chessGame = new Game();
		GameView chessGameView = new GameView(chessGame);
	}
}
