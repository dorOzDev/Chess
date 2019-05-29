package movement;

import game.Spot;
import pieces.Piece;

public class EnPassntAttack extends AttackMove  {

	public EnPassntAttack(Spot sourceSpot, Spot destSpot, Piece piece, Piece attackedPiece) {
		super(sourceSpot, destSpot, piece, attackedPiece);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isEnPassntMove() {
		return true;
	}

}
