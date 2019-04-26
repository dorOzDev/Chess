package movement;

import enaum.MoveStatus;
import enaum.PieceType;
import game.Board;
import game.Spot;
import soldiers.Piece;

public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	protected Piece piece;
	protected MoveStatus moveStatus;
	protected Board board;
	
	public Move(final Spot sourceSpot,final Spot destSpot, final Piece piece){
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
		this.piece = piece;
		moveStatus = MoveStatus.NOT_DONE;
		board = Board.startNewBoard();
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
	
	public abstract boolean isAttackMove();
	
	public abstract Piece getAttackedPiece();
	
	@Override
	public String toString() {
		return board.toAlgebricCordinate(destSpot);
	}

}
