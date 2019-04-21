package movement;

import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	private Piece piece;
	
	public Move(final Spot sourceSpot,final Spot destSpot, final Piece piece){
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
		this.piece = piece;
	}
	
	public Spot getSourceSpot() {
		return sourceSpot;
	}
	
	public Spot getDestSpot() {
		return destSpot;
	}

	public Piece getPieceToMove() {
		return piece;
	}

}
