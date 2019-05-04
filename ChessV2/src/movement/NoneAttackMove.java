package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class NoneAttackMove extends Move {
	
	
	public NoneAttackMove(Spot sourceSpot, Spot destSpot, Piece piece) {
		super(sourceSpot, destSpot, piece, MoveType.NONE_ATTACK_MOVE);
		
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
		throw new ExceptionInInitializerError("Shounld't reach here");
	}
	

}
