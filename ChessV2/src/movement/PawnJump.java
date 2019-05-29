package movement;

import game.Board;
import game.Spot;
import pieces.Piece;

public class PawnJump extends ProgressMove {
	
	
	
	public PawnJump(final Spot sourceSpot,final Spot destSpot,final  Piece piece, Board board) {
		super(sourceSpot, destSpot, piece, board);
		
		
	}
	
	@Override
	public boolean isPawnJumpMove() {
		return true;
	}

}
