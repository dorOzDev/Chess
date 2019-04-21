package movement;

import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public class NoneAttackMove extends Move {
	
	
	public NoneAttackMove(Spot sourceSpot, Spot destSpot, Piece piece) {
		super(sourceSpot, destSpot, piece);
		
	}

}
