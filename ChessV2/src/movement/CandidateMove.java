package movement;

import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class CandidateMove extends Move {

	public CandidateMove(Spot sourceSpot, Spot destSpot, Piece piece) {
		super(sourceSpot, destSpot, piece);
		
	}

	@Override
	public boolean isAttackMove() {
		
		return false;
	}

	@Override
	public Piece getAttackedPiece() {
		
		throw new RuntimeException("None attack move, shouldn't reach here!@#$%");
	}
	
	

}
