package player;

import java.util.ArrayList;

import enaum.PlayerColour;
import game.Board;

import movement.Move;
import soldiers.Piece;

public class PlayerWhite extends Player {
	
	public PlayerWhite(ArrayList<Piece> remainingPieces, Board board){
		super(remainingPieces, board, PlayerColour.WHITE);
		remainingPieces = board.getPiecesWhite();
	}
	
	@Override
	protected boolean isInCheck() {
		return board.getInCheckStatusWhitePlayer();
	}
	
	@Override
	public boolean isInCheckMate() {
		return board.isInCheckMateWhitePlayer();
	}
	
	@Override
	public boolean isInStaleMate() {
		return board.isInStaleMateWhitePlayer();
	}

	@Override
	public boolean isWhite() {
		return true;
	}

	@Override
	public boolean isBlack() {
		return false;
	}

	@Override
	public void updateCurrentAvailablePieces() {
		remainingPieces = board.getPiecesWhite();
		
	}
	
}

