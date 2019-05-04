package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class AttackMove extends Move {
	
	private Piece attackedPiece;
	public AttackMove(Spot sourceSpot, Spot destSpot, final Piece piece, final Piece attackedPiece) {
		super(sourceSpot, destSpot, piece, MoveType.ATTACK_MOVE);
		this.attackedPiece = attackedPiece;
		
	}

	@Override
	public boolean isAttackMove() {
		return true;
	}
	
	@Override
	public Piece getAttackedPiece() {
		return attackedPiece;
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
