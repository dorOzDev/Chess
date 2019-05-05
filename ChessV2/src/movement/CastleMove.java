package movement;

import enaum.MoveType;
import game.Spot;
import soldiers.Piece;

public abstract class CastleMove extends NoneAttackMove {

	public CastleMove(Spot sourceSpot, Spot destSpot, Piece piece) {
		super(sourceSpot, destSpot, piece);
	}
	
	@Override
	public boolean isCastleMove() {
		return true;
	}
	
	@Override
	public abstract Spot getRookDestSpot();
	
	@Override
	public abstract Spot getRookSourceSpot();
	
	@Override 
	public abstract Piece getRook();

}
