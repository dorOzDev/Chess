package movement;

import game.Spot;
import soldiers.Piece;

public class PawnJump extends ProgressMove {
	
	
	
	public PawnJump(final Spot sourceSpot,final Spot destSpot,final  Piece piece) {
		super(sourceSpot, destSpot, piece);
		
		
	}
	
	@Override
	public boolean isPawnJumpMove() {
		return true;
	}

}
