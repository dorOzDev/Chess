package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Board;
import game.Spot;
import pieces.Piece;


/*
 * This class is holding information regard each move in the game, it holds source location, destination location move type
 * */
public abstract class Move {
	
	protected Spot sourceSpot;
	protected Spot destSpot;
	protected Piece piece;
	protected MoveType moveType;
	Board board;
	
	
	public Move(final Spot sourceSpot,final Spot destSpot, final Piece piece, MoveType moveType, Board board){
		this.board = board;
		this.destSpot = destSpot;
		this.sourceSpot = sourceSpot;
		this.piece = piece;
		this.moveType = moveType;
		
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
		return moveType;
	}
	
	public abstract boolean isAttackMove();
	
	public abstract boolean isCastleMove();
	
	public abstract boolean isCastleAllowed();
	
	public abstract Piece getAttackedPiece();
	
	@Override
	public String toString() {
		return board.castToBoardCordinate(destSpot);
	}
	
	public abstract Spot getRookDestSpot();
	
	public abstract Spot getRookSourceSpot();
	
	public abstract Piece getRook();
	
	public abstract boolean isPawnJumpMove();
	
	public abstract boolean isEnPassntMove();

}
