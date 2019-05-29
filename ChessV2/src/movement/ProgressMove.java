package movement;

import enaum.MoveType;
import enaum.PieceType;
import game.Board;
import game.Spot;
import pieces.Piece;

public class ProgressMove extends Move {
	
	
	public ProgressMove(Spot sourceSpot, Spot destSpot, Piece piece, Board board) {
		super(sourceSpot, destSpot, piece, MoveType.PROGRESS_MOVE, board);
		
	}

	@Override
	public boolean isAttackMove() {
		return false;
	}
	
	@Override
	public Piece getAttackedPiece() {
		throw new RuntimeException("None attack move, shouldn't reach here!@#$%");
	}

	@Override
	public boolean isCastleMove() {
		
		return false;
	}

	@Override
	public boolean isCastleAllowed() {
		throw new ExceptionInInitializerError("Shounld't reach here");
	}

	@Override
	public Spot getRookDestSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}
	
	@Override
	public Spot getRookSourceSpot() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}
	
	@Override
	public Piece getRook() {
		throw new RuntimeException("Shouldn't reach here, none castle move");
	}

	@Override
	public boolean isPawnJumpMove() {
		return false;
	}

	@Override
	public boolean isEnPassntMove() {
		// TODO Auto-generated method stub
		return false;
	}

}
