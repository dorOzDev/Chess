package movement;

import enaum.MoveStatus;
import enaum.PieceType;
import game.Spot;
import soldiers.Piece;

public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	protected Piece piece;
	protected MoveStatus moveStatus;
	
	public Move(final Spot sourceSpot,final Spot destSpot, final Piece piece){
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
		this.piece = piece;
		moveStatus = MoveStatus.NOT_DONE;
	}
	
	public Spot getSourceSpot() {
		return sourceSpot;
	}
	
	public Spot getDestSpot() {
		return destSpot;
	}

	public Piece getPiece() {
		return piece;
	}
	
	public boolean isMoveDone() {
		return moveStatus == MoveStatus.DONE;
	}

}
