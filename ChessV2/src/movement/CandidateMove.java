package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class CandidateMove extends Move {

	public CandidateMove(Spot sourceSpot, Spot destSpot, Piece piece) {
		super(sourceSpot, destSpot, piece, MoveType.UNKNOWN);
		
	}

	@Override
	public boolean isAttackMove() {
		
		return false;
	}

	@Override
	public Piece getAttackedPiece() {
		
		throw new RuntimeException("None attack move, shouldn't reach here!@#$%");
	}

	@Override
	public boolean isCastleMove() {
		
		return false;
	}
	
	
	@Override
	public boolean isCastleAllowed() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}
	

	@Override
	public Spot getRookDestSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}
	
	@Override
	public Spot getRookSourceSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}
	
	@Override
	public Piece getRook() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public boolean isPawnJumpMove() {
		return false;
	}

	@Override
	public boolean isEnPassntMove() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
