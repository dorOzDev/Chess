package player;

import java.util.ArrayList;

import enaum.PlayerColour;
import game.Board;
import movement.Move;
import soldiers.Piece;

public class PlayerBlack extends Player {
	
	public PlayerBlack(ArrayList<Piece> remainingPieces,Board board){
		super(remainingPieces, board, PlayerColour.BLACK);
		remainingPieces = board.getPiecesBlack();
	}

	@Override
	protected boolean isInCheck() {	
		return board.getInCheckStatusBlackPlayer();
		
	}

	@Override
	public boolean isInCheckMate() {
		
		return board.isInCheckMateBlackPlayer();
	}

	@Override
	public boolean isInStaleMate() {
		return board.isInStaleMateBlackPlayer();
	}

	@Override
	public boolean isWhite() {
		return false;
	}

	@Override
	public boolean isBlack() {
		
		return true;
	}

	@Override
	public void updateCurrentAvailablePieces() {
		remainingPieces = board.getPiecesBlack();
		
	}
	
	 
	

	
	

}
 