package player;

import java.util.ArrayList;

import enaum.PlayerColour;
import game.Board;

import movement.Move;
import soldiers.Piece;

public class PlayerWhite extends Player {
	
	public PlayerWhite(ArrayList<Piece> remainingPieces, Board board){
		super(remainingPieces, board, PlayerColour.WHITE);
	}
	
	@Override
	protected boolean isInCheck() {
		legalOpponentMoves = board.getAllLegalBlackMoves();	
		for (Move move : legalOpponentMoves) {
			if(king.getSpot() == move.getDestSpot())
				return  true;
		}
		return  false;
	}
	
	@Override
	public boolean isInCheckMate() {
		legalMoves = board.getAllLegalWhiteMoves();
		filterInChessMoves(legalMoves);		
		if(getInCheckStatus() && legalMoves.isEmpty()) // If king is in chess threat and no legal moves left the king is in checkmate.
			return true;
		return false;
	}
	
	@Override
	public boolean isInStaleMate() {
		legalMoves = board.getAllLegalWhiteMoves();
		if(!getInCheckStatus()  && legalMoves.isEmpty()) // If king is not in chess but no legal moves left 
			return true;
		return false;
	}
	
}

