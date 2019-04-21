package player;

import java.util.ArrayList;

import game.Board;
import movement.Move;
import soldiers.Piece;

public class PlayerBlack extends Player {
	
	public PlayerBlack(ArrayList<Piece> remainingPieces,Board board){
		super(remainingPieces, board);		
	}

	@Override
	protected boolean isInCheck() {
		this.legalOpponentMoves = board.getAllLegalWhiteMoves();
		
		for (Move move : legalOpponentMoves) {
			if(king.getPieceSpot() == move.getDestSpot())
				return  true;
		}
		return  false;
	}

	@Override
	public boolean isInCheckMate() {

		if(isInCheck && legalMoves.isEmpty()) // If king is in chess threat and no legal moves left the king is in checkmate.
			return true;
		return false;
	}
	
	 
	

	
	

}
 