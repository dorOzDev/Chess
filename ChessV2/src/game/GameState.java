package game;

import java.util.ArrayList;

import enaum.PlayerColour;
import pieces.Piece;
import player.Player;

import game.*;
import game.Board.BoardBuilder;

public class GameState {
	/************
	 * This class saves the current state of the board before changing to a new Board i shall pass thru here to make the transition bugless.
	 * This class is designed in a Singleton way.
	 */
	
	private static GameState gameState = null;
	Board sourceBoard;
	/*	
	Player currPlayer;
	ArrayList<Piece> playerBlackPieces;
	ArrayList<Piece> playerWhitePieces;
	Spot [][] boardSpots;
	*/
	
	
	public GameState(Board board){
		this.sourceBoard = board;
		
	}
	
	public static GameState get(Board board) {
		if(gameState == null) {
			gameState = new GameState(board);
		}
		return gameState;
	}
	

	public Board getUpdatedBoard(Board newBoard) {
		
		this.sourceBoard = new BoardBuilder(newBoard.getPiecesBlack(), newBoard.getPiecesWhite()).build();

		return sourceBoard;
	}



}