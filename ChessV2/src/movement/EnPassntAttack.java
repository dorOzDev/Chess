package movement;

import game.Board;
import game.Spot;
import pieces.Piece;

public class EnPassntAttack extends AttackMove  {

	public EnPassntAttack(Spot sourceSpot, Spot destSpot, Piece piece, Piece attackedPiece, Board board) {
		super(sourceSpot, destSpot, piece, attackedPiece, board);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isEnPassntMove() {
		return true;
	}

}
