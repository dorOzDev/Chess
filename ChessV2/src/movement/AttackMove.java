package movement;

import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class AttackMove extends Move {
	
	private Piece attackedPiece;
	public AttackMove(Spot sourceSpot, Spot destSpot, final Piece piece, final Piece attackedPiece) {
		super(sourceSpot, destSpot, piece);
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


}
