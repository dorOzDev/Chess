package player;

import java.util.ArrayList;

import enaum.PlayerColour;
import game.Board;
import movement.Move;
import soldiers.Piece;

public class PlayerBlack extends Player {
	
	public PlayerBlack(ArrayList<Piece> remainingPieces,Board board){
		super(remainingPieces, board, PlayerColour.BLACK);		
	}

	@Override
	protected boolean isInCheck() {
		this.legalOpponentMoves = board.getAllLegalWhiteMoves();
		
		for (Move move : legalOpponentMoves) {
			if(king.getSpot() == move.getDestSpot())
				return  true;
		}
		return  false;
	}

	@Override
	public boolean isInCheckMate() {
		
		legalMoves = board.getAllLegalBlackMoves();
		filterInChessMoves(legalMoves);		
		if(getInCheckStatus() && legalMoves.isEmpty()) // If king is in chess threat and no legal moves left the king is in checkmate.
			return true;
		return false;
	}

	@Override
	public boolean isInStaleMate() {
		legalMoves = board.getAllLegalBlackMoves();
		if(!getInCheckStatus() && legalMoves.isEmpty()) // If king is not in chess but no legal moves left 
			return true;
		return false;
	}
	
	 
	

	
	

}
 