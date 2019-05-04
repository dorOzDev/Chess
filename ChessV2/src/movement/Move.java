package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Board;
import game.Spot;
import soldiers.Piece;

public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	protected Piece piece;
	protected MoveType moveType;
	protected Board board;
	
	public Move(final Spot sourceSpot,final Spot destSpot, final Piece piece, MoveType moveType){
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
		this.piece = piece;
		moveType = moveType;
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
	
	public MoveType getMoveType() {
		return this.moveType;
	}
	
	public abstract boolean isAttackMove();
	
	public abstract boolean isCastleMove();
	
	public abstract boolean isCastleAllowed();
	
	public abstract Piece getAttackedPiece();
	
	@Override
	public String toString() {
		return board.castToBoardCordinate(destSpot);
	}
	
	

}
